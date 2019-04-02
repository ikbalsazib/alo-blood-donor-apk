package com.aloblooddonor.bd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccount extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    FirebaseUser user;
    String userid;

    Button buttonLogOut;
    TextView profileName, profileBlood, profilePhone, profileEmail, profileAddress, profileAge, profileWeight;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        mAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        profileName = findViewById(R.id.profileName);
        profileBlood = findViewById(R.id.profileBlood);
        profilePhone = findViewById(R.id.profilePhone);
        profileAge = findViewById(R.id.profileAge);
        profileWeight = findViewById(R.id.profileWeight);
        profileEmail = findViewById(R.id.profileEmail);
        profileAddress = findViewById(R.id.profileAddress);

        progressBar = findViewById(R.id.progressbar);



        buttonLogOut = findViewById(R.id.buttonLogOut);

        showInformation();


        //LogOut Activity....
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(UserAccount.this,SignInActivity.class));
            }
        });

    }


    //Display Information Function..
    private void showInformation() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.GONE);
                String name = dataSnapshot.child("name").getValue().toString();
                String blood = dataSnapshot.child("blood").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String age = dataSnapshot.child("age").getValue().toString();
                String weight = dataSnapshot.child("weight").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();

                profileName.setText(name);
                profileBlood.setText("Blood Group: " + blood);
                profilePhone.setText("Phone No: " + phone);
                profileAge.setText("Age: " + age + " Years");
                profileWeight.setText("Weight: " + weight + " Kg");
                profileAddress.setText("Address: " + address);
                profileEmail.setText("Email: " + email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

}



