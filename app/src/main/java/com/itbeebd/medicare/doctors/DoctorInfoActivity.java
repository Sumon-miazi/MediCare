package com.itbeebd.medicare.doctors;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.DoctorChamberAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.dataClasses.DoctorChamber;

import java.util.ArrayList;

public class DoctorInfoActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<DoctorChamber> {

    private RecyclerView allDoctorChamberRecyclerView;
    private DoctorChamberAdapter doctorChamberListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);

        allDoctorChamberRecyclerView = findViewById(R.id.doctorProfileChamberRecyclerId);
        doctorChamberListAdapter = new DoctorChamberAdapter(this);

        ArrayList<DoctorChamber> doctorChambers = new ArrayList<>();
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());

        doctorChamberListAdapter.setItems(doctorChambers);
        doctorChamberListAdapter.setListener(this);
        allDoctorChamberRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        allDoctorChamberRecyclerView.setAdapter(doctorChamberListAdapter);
        /*
        if (getIntent().hasExtra("hospitalId")) {
            int id = getIntent().getIntExtra("hospitalId", 0);
            new ApiCalls().getAllDoctorByHospitalId(id, (doctors, message) -> {
                if (doctors != null) {
                    doctorChamberListAdapter.setItems(doctors);
                    doctorChamberListAdapter.setListener(this);
                    allDoctorChamberRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    allDoctorChamberRecyclerView.setAdapter(doctorChamberListAdapter);
                } else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            });
        }

         */
    }

    @Override
    public void onItemClicked(DoctorChamber item) {
        System.out.println(">>>>>>>>> doctor chamber name clicked");
    }
}