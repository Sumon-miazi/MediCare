package com.itbeebd.medicare.userProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.appointmentAndReport.AppointmentListActivity;
import com.itbeebd.medicare.appointmentAndReport.ReportListActivity;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.db.Dao;
import com.itbeebd.medicare.utils.Patient;

public class UserProfileActivity extends Fragment {

    private UserProfileActivity.OnItemSelectedListener listener;
    private ImageView patientProfileImageView;
    private Patient patient = null;
    private ConstraintLayout showAllAppointmentBtn;
    private ConstraintLayout showAllReportsBtn;
    private ConstraintLayout editProfileBtn;
    private ConstraintLayout logOutBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_user_profile, container, false);

        patientProfileImageView = view.findViewById(R.id.patientProfileImageViewId);
        showAllAppointmentBtn = view.findViewById(R.id.showAllAppointmentBtn);
        showAllReportsBtn = view.findViewById(R.id.showAllReportsBtn);
        editProfileBtn = view.findViewById(R.id.editProfileBtn);
        logOutBtn = view.findViewById(R.id.logOutBtn);

        patient = new Dao().getPatientDetails(CustomSharedPref.getInstance(getContext()).getUserId());

        if(patient != null && patient.getImage() != null){
            Glide.with(getContext())
                    .load(ApiUrls.BASE_IMAGE_URL + patient.getImage())
                    .into(patientProfileImageView);
        }

        showAllAppointmentBtn.setOnClickListener(this::showAllAppointments);
        showAllReportsBtn.setOnClickListener(this::showAllReports);
        editProfileBtn.setOnClickListener(this::editProfile);
        logOutBtn.setOnClickListener(v -> listener.onItemSelectedOnDashBoard(v));

        return view;
    }

    private void showAllAppointments(View view){
        startActivity(new Intent(getContext(), AppointmentListActivity.class));
    }

    private void showAllReports(View view){
        Intent intent = new Intent(getContext(), ReportListActivity.class);
        intent.putExtra("show_all_reports",1);
        startActivity(intent);
    }
    private void editProfile(View view){
      //  startActivity(new Intent(getContext(), AppointmentListActivity.class));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserProfileActivity.OnItemSelectedListener) {
            listener = (UserProfileActivity.OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implemenet MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnItemSelectedListener {
        void onItemSelectedOnDashBoard(View view);
    }
}