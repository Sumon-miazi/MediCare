package com.itbeebd.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.itbeebd.medicare.hospitals.HospitalListActivity;
import com.itbeebd.medicare.utils.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;

public class DashBoardActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_dash_board, container, false);
    }

    /*
    private CircularImageView main_doctorImageView;
    private CardView hospitalCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        main_doctorImageView = findViewById(R.id.main_doctorImageViewId);
        hospitalCardView = findViewById(R.id.hospitalCardViewId);

        hospitalCardView.setOnClickListener(view -> {
            startActivity(new Intent(this, HospitalListActivity.class));
            // startActivity(new Intent(this, DoctorAppointmentActivity.class));
        });
    }

     */
}