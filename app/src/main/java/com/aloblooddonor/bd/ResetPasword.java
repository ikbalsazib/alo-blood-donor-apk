package com.aloblooddonor.bd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasword extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText resetEmail;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pasword);

        //Firebase Database Connector..
        mAuth = FirebaseAuth.getInstance();

        resetEmail = findViewById(R.id.resetEmail);
        resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = resetEmail.getText().toString().trim();

                if (useremail.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please Enter Your Email to reset password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()) {
                               Toast.makeText(getApplicationContext(),"Password Reset Email Sent Successfully",Toast.LENGTH_SHORT).show();
                               finish();
                               startActivity(new Intent(ResetPasword.this,SignInActivity.class));
                           }
                           else
                           {
                               Toast.makeText(getApplicationContext(),"Error! Something went wrong",Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                }
            }
        });
    }
}
