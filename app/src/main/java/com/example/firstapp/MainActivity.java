package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView shopa, shopb, shopc;
    Button button;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth  = FirebaseAuth.getInstance();
        shopa = findViewById(R.id.shopa);
        shopb = findViewById(R.id.shopb);
        shopc = findViewById(R.id.shopc);
        //button = findViewById(R.id.logout);

        shopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShopaActivity.class));
            }
        });
        shopb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Choose Shop 1", Toast.LENGTH_SHORT).show();
            }
        });
        shopc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Choose Shop 1", Toast.LENGTH_SHORT).show();
            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//                startActivity(new Intent(MainActivity.this, FirstActivity.class));
//                finish();
//                Toast.makeText(MainActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
