package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ShopaActivity extends AppCompatActivity {

    private DatabaseReference databaseReference, userRef;
    private EditText editText;
    String message, date, time, name;
    FirebaseAuth firebaseAuth;
    FloatingActionButton floatingActionButton;
    ArrayList<Chatposts> chatposts;
    Adapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopa);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        userRef = FirebaseDatabase.getInstance().getReference().child("Shop Holders");

        editText = findViewById(R.id.editShopa);
        floatingActionButton = findViewById(R.id.floata);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = editText.getText().toString();

                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(ShopaActivity.this, "Type anything", Toast.LENGTH_SHORT).show();
                }
                else {
                    storageInfo();
                }
            }
        });

        recyclerView = findViewById(R.id.recyclershopa);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatposts = new ArrayList<Chatposts>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Chatposts h = dataSnapshot1.getValue(Chatposts.class);
                    chatposts.add(h);
                }

                adapter = new Adapter(ShopaActivity.this, chatposts);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void storageInfo() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        date = simpleDateFormat.format(calendar.getTime());

        Calendar calendarTime = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("hh:mm:ss");
        time = simpleDateFormatTime.format(calendarTime.getTime());

        name = date + time;

        userRef.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String usershop = dataSnapshot.child("usershop").getValue().toString();

                    HashMap<String, Object> postMap = new HashMap<>();
                    postMap.put("message", message);
                    postMap.put("time", time);
                    postMap.put("usershop", usershop);

                    databaseReference.child(firebaseAuth.getCurrentUser().getUid() + name).updateChildren(postMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(ShopaActivity.this, ShopaActivity.class));
                                        finish();
                                        //Toast.makeText(ShopaActivity.this, "Post updated successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ShopaActivity.this, "Error occurred! Try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
