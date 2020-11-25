package com.itbeebd.medicare.userProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.db.Dao;
import com.itbeebd.medicare.utils.Patient;

public class UserProfileActivity extends Fragment {

    private ImageView patientProfileImageView;
    private Patient patient = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_user_profile, container, false);

        patientProfileImageView = view.findViewById(R.id.patientProfileImageViewId);
        patient = new Dao().getPatientDetails(CustomSharedPref.getInstance(getContext()).getUserId());

        if(patient != null && patient.getImage() != null){
            Glide.with(getContext())
                    .load(ApiUrls.BASE_IMAGE_URL + patient.getImage())
                    .into(patientProfileImageView);
        }

        return view;
    }


}