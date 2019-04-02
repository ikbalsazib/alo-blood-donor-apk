package com.aloblooddonor.bd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private CardView findBlood, contactUs, aboutUs, userProfile, becomeBloodDonor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        //Define CardView Variable with ID..
        findBlood = findViewById(R.id.find_blood);
        contactUs = findViewById(R.id.contact_us);
        aboutUs = findViewById(R.id.about_us);
        userProfile = findViewById(R.id.user_profile);
        becomeBloodDonor = findViewById(R.id.become_blood_donor);

        //Adding Click Listener Function on CardView..
        findBlood.setOnClickListener(this);
        contactUs.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        userProfile.setOnClickListener(this);
        becomeBloodDonor.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.find_blood : i = new Intent(this,FindBlood.class);
            startActivity(i);
            break;

            case R.id.contact_us : i = new Intent(this,ContactUs.class);
            startActivity(i);
            break;

            case R.id.about_us : i = new Intent(this,AboutUs.class);
            startActivity(i);
            break;

            case R.id.user_profile :
                if (mAuth.getCurrentUser() != null){
                    i = new Intent(this,UserAccount.class);
                    startActivity(i);
                }
                else {
                    startActivity(new Intent(Home.this,SignInActivity.class));
                }

            break;

            case R.id.become_blood_donor : i = new Intent(this,DonoteBlood.class);
            startActivity(i);
            break;

            default: break;


        }
    }
}
