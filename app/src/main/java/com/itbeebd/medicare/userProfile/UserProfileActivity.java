package com.itbeebd.medicare.userProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.itbeebd.medicare.R;

public class UserProfileActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_user_profile, container, false);
    }

/*
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

 */
}