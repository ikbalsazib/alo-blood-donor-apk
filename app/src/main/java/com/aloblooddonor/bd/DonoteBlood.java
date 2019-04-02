package com.aloblooddonor.bd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class DonoteBlood extends AppCompatActivity {

    Button btnGoSignIn, btnGoSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donote_blood);

        mAuth = FirebaseAuth.getInstance();

        btnGoSignIn = findViewById(R.id.btnGoSignIn);
        btnGoSignUp = findViewById(R.id.btnGoSignUp);

        btnGoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() == null) {
                    Intent signIn = new Intent(DonoteBlood.this, SignInActivity.class);
                    startActivity(signIn);
                }
                else {
                    Intent signIn = new Intent(DonoteBlood.this, SignInActivity.class);
                    startActivity(signIn);
                    Toast.makeText(getApplicationContext(),"You are already Sign in as a blood donor",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnGoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() == null) {
                    Intent signUp = new Intent(DonoteBlood.this, SignUpActivity.class);
                    startActivity(signUp);
                }
                else {
                    Intent signUp = new Intent(DonoteBlood.this, SignUpActivity.class);
                    startActivity(signUp);
                    Toast.makeText(getApplicationContext(),"You are already Signed in as a blood donor! For Sign up logout first",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

}
