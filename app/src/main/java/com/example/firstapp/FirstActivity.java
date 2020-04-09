package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirstActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText editText, password, shopName;
    Spinner spinner;
    Button button;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Shop Holders");

        editText = findViewById(R.id.email);
        shopName = findViewById(R.id.shopHolder);
        password = findViewById(R.id.password);
        button = findViewById(R.id.register);
        spinner = findViewById(R.id.shopCate);
        progressDialog = new ProgressDialog(this);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Validating");
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                final String useremail = editText.getText().toString();
                final String passuser = password.getText().toString();
                final String usershop = shopName.getText().toString();


                if (TextUtils.isEmpty(usershop)) {
                    progressDialog.dismiss();
                    Toast.makeText(FirstActivity.this, "Type Shop name", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(useremail)) {
                    progressDialog.dismiss();
                    Toast.makeText(FirstActivity.this, "Enter mail", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(passuser)) {
                    progressDialog.dismiss();
                    Toast.makeText(FirstActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }

                else {
                    //progressDialog.dismiss();
                    firebaseAuth.createUserWithEmailAndPassword(useremail, passuser)
                            .addOnCompleteListener(FirstActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

//                                       Toast.makeText(FirstActivity.this, "Paasing",
//                                                Toast.LENGTH_SHORT).show();
                            Register register = new Register(useremail, usershop);
//
                            FirebaseDatabase.getInstance().getReference(databaseReference.getKey())
                                    .child(firebaseAuth.getCurrentUser().getUid())
                                    .setValue(register).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                       progressDialog.dismiss();
                                        Toast.makeText(FirstActivity.this, "Hello " +usershop+ "... Welcome to Shopitizer!",
                                                Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(FirstActivity.this, MainActivity.class));
                                        finish();
                                    }
                                    });

                                    }
                                    else {

                                        progressDialog.dismiss();
                                        Exception firebaseAuthException = task.getException();
                                        Toast.makeText(FirstActivity.this, "Authentication failed! " +firebaseAuthException,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                      }
                                    });
                                }
}
                });
            }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            startActivity(new Intent(FirstActivity.this, MainActivity.class));
            finish();
        }
    }
}

