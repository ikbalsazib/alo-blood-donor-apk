package com.aloblooddonor.bd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword, editTextName, editTextPhone, editTextBlood, editTextAddress, editTextAge, editTextWeight;
    Button buttonSignUp, btnGoSignIn;
    ProgressBar progressBar;
    String codeSent;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Firebase Database Connector..
        mAuth = FirebaseAuth.getInstance();

        //For Get User Details Information Text
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextBlood = findViewById(R.id.editTextBlood);
        editTextAge = findViewById(R.id.editTextAge);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextAddress = findViewById(R.id.editTextAddress);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        btnGoSignIn = findViewById(R.id.btnGoSignIn);
        progressBar = findViewById(R.id.progressbar);

        buttonSignUp.setOnClickListener(this);
        btnGoSignIn.setOnClickListener(this);

        //Make Blood Group Input UPPERCASE...
        editTextBlood.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }
            @Override
            public void afterTextChanged(Editable et) {
                String s=et.toString();
                if(!s.equals(s.toUpperCase()))
                {
                    s=s.toUpperCase();
                    editTextBlood.setText(s);
                }
                editTextBlood.setSelection(editTextBlood.getText().length());
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(SignUpActivity.this, UserAccount.class));
        }
    }

    //Main Registration Function..
    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String name = editTextName.getText().toString().toUpperCase().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final String blood = editTextBlood.getText().toString().trim();
        final String age = editTextAge.getText().toString().trim();
        final String weight = editTextWeight.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();

        //Input Condition Apply..
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
            editTextPassword.setError("Password must be longer than 6 character");
            editTextPassword.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editTextName.setError("Name is Required");
            editTextName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            editTextPhone.setError("Phone No is Required");
            editTextPhone.requestFocus();
            return;
        }

        if (blood.isEmpty()) {
            editTextBlood.setError("Blood Group is Required");
            editTextBlood.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            editTextAge.setError("Age is Required");
            editTextAge.requestFocus();
            return;
        }

        if (weight.isEmpty()) {
            editTextWeight.setError("Weight is Required");
            editTextWeight.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            editTextAddress.setError("Address is Required");
            editTextAddress.requestFocus();
            return;
        }


        //Active ProgressBar...
        progressBar.setVisibility(View.VISIBLE);

        //Save Register Data on Firebase Auth..
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    UserInfo user = new UserInfo(
                            email,
                            name,
                            phone,
                            blood,
                            age,
                            weight,
                            address
                    );

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);

                                sentEmailVerification();
//                                Intent signUpSuccess = new Intent(SignUpActivity.this, SignInActivity.class);
////                                signUpSuccess.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                                startActivity(signUpSuccess);
                            }

                            else{
                                Toast.makeText(getApplicationContext(),"Sorry! Something happened wrong! TRY AGAIN",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        progressBar.setVisibility(View.GONE);
                        //Toast.makeText(getApplicationContext(),"Sorry! This Email is already registered",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Something went Wrong!Please try again later",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //Set Button Click Activity..
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonSignUp:
                registerUser();
                break;

            case R.id.btnGoSignIn:
                finish();
                startActivity(new Intent(this, SignInActivity.class));
                break;
        }
    }

    //Verification Email Sending..
    private void sentEmailVerification() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this,"Verification Email Sent. Please check your email for verification",Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        finish();
                        Intent signUpSuccess = new Intent(SignUpActivity.this, SignInActivity.class);
                        signUpSuccess.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(signUpSuccess);
                    }
                }
            });
        }
    }



//    private void sentPhoneVerificationCode() {
//
//        String phone = editTextPhone.getText().toString().trim();
//        if (phone.isEmpty()) {
//            editTextPhone.setError("Phone No is Required");
//            editTextPhone.requestFocus();
//            return;
//        }
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phone,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks
//    }
//
//
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//
//        }
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//
//            codeSent = s;
//        }
//    };



//    private void verifyCode() {
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
//    }
}
