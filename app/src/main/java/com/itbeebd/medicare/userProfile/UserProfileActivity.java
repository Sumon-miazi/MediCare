package com.itbeebd.medicare.userProfile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.dataClasses.Patient;

public class UserProfileActivity extends AppCompatActivity {

    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        if (getIntent().hasExtra("patient")) {
            this.patient = getIntent().getParcelableExtra("patient");

            if (this.patient != null) setPatientProfile();
        }
    }

    private void setPatientProfile() {

    }
}