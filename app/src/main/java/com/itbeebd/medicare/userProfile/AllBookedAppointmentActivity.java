package com.itbeebd.medicare.userProfile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;

public class AllBookedAppointmentActivity extends AppCompatActivity {

    private RecyclerView allBookedAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_booked_appointment);

        allBookedAppointment = findViewById(R.id.allBookedAppointmentId);
    }
}