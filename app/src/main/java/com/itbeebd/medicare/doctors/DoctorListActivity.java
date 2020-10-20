package com.itbeebd.medicare.doctors;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.DoctorAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiCalls;
import com.itbeebd.medicare.dataClasses.Doctor;

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
            new ApiCalls().getAllDoctorByHospitalId(id, (doctors, message) -> {
                if (doctors != null) {
                    doctorListAdapter.setItems(doctors);
                    doctorListAdapter.setListener(this);
                    allDoctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    allDoctorRecyclerView.setAdapter(doctorListAdapter);
                } else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onItemClicked(Doctor item) {
        System.out.println(">>>>>>>>> doctor list clicked" + item.getName());
        startActivity(new Intent(this, DoctorInfoActivity.class));
    }
}