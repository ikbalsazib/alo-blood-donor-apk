package com.aloblooddonor.bd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.oob.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    TextView forgetPassword;
    Button buttonSignIn, GoSignUp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        forgetPassword = findViewById(R.id.forgetPassword);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,ResetPasword.class));
            }
        });


        progressBar = findViewById(R.id.progressbar);

        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(this);

        GoSignUp = findViewById(R.id.btnGoSignUp);
        GoSignUp.setOnClickListener(this);
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid Email Address");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }


        if (password.length()<6) {
            editTextPassword.setError("Password must be Longer");
            editTextPassword.requestFocus();
            return;
        }

        //Active ProgressBar...
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()) {
                   progressBar.setVisibility(View.GONE);
                   finish();
                   //Intent loginSuccess = new Intent(SignInActivity.this, Home.class);
                   //loginSuccess.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   //startActivity(loginSuccess);
                   checkEmailVerification();
                   //Toast.makeText(getApplicationContext(),"Login Success! Thanks for Login",Toast.LENGTH_SHORT).show();
               }
               else
               {
                   progressBar.setVisibility(View.GONE);
                   Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    //User Login Check..
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, UserAccount.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoSignUp:
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.buttonSignIn:
                userLogin();
                break;
        }
    }


    private void checkEmailVerification() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        if (emailFlag) {
            finish();
            Intent loginSuccess = new Intent(SignInActivity.this, Home.class);
            loginSuccess.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginSuccess);
            Toast.makeText(getApplicationContext(),"Login Success! Thanks for Login",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Please Verify Your Email First",Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }



}
