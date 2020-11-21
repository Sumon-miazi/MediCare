package com.itbeebd.medicare.doctors;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.doctor.DoctorAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.DoctorApi;
import com.itbeebd.medicare.utils.Doctor;

public class DoctorListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<Doctor> {

    private RecyclerView allDoctorRecyclerView;
    private DoctorAdapter doctorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        allDoctorRecyclerView = findViewById(R.id.allDoctorRecyclerViewId);
        doctorListAdapter = new DoctorAdapter(this);

        if (getIntent().hasExtra("hospitalId")) {
            int id = getIntent().getIntExtra("hospitalId", 0);
            new DoctorApi().getAllDoctorByHospitalId(id, (doctors, message) -> {
                if (doctors != null) {
                    doctorListAdapter.setItems(doctors);
                    doctorListAdapter.setListener(this);
                    allDoctorRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    allDoctorRecyclerView.setAdapter(doctorListAdapter);
                } else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            });
        }
        else if (getIntent().hasExtra("specialistId")) {
            int id = getIntent().getIntExtra("specialistId", 0);
            new DoctorApi().getAllDoctorBySpecialistId(id, (doctors, message) -> {
                if (doctors != null) {
                    doctorListAdapter.setItems(doctors);
                    doctorListAdapter.setListener(this);
                    allDoctorRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    allDoctorRecyclerView.setAdapter(doctorListAdapter);
                } else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onItemClicked(Doctor item, View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.doctorAppointmentTxtId:
                intent = new Intent(this, DoctorAppointmentActivity.class);
                intent.putExtra("doctorObj", item);
                startActivity(intent);
                break;
            case R.id.doctorInfoLayoutId:
                intent = new Intent(this, DoctorInfoActivity.class);
                intent.putExtra("doctorObj", item);
                startActivity(intent);
                break;
        }
        // startActivity(new Intent(this, DoctorInfoActivity.class));
    }
}