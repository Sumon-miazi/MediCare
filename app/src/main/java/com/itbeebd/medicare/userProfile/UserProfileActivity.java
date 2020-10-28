package com.itbeebd.medicare.userProfile;

import android.os.Bundle;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.utils.NavigationView;
import com.itbeebd.medicare.utils.Patient;

public class UserProfileActivity extends NavigationView {

    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        super.onCreateDrawer();

        if (getIntent().hasExtra("patient")) {
            this.patient = getIntent().getParcelableExtra("patient");

            if (this.patient != null) setPatientProfile();
        }
    }

    private void setPatientProfile() {

    }
}