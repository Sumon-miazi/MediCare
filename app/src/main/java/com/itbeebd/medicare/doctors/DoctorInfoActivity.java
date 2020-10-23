package com.itbeebd.medicare.doctors;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.DoctorChamberAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiCalls;
import com.itbeebd.medicare.dataClasses.Doctor;
import com.itbeebd.medicare.dataClasses.DoctorChamber;

import java.util.ArrayList;

public class DoctorInfoActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<DoctorChamber> {

    private TextView doctorName;
    private TextView doctorQualification;
    private TextView totalPatient;
    private TextView totalReview;
    private TextView about;

    private RecyclerView allDoctorChamberRecyclerView;
    private DoctorChamberAdapter doctorChamberListAdapter;
    private Doctor doctor;
    private ArrayList<DoctorChamber> doctorChambers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);

        doctorName = findViewById(R.id.doctorProfileNameTxtViewId);
        doctorQualification = findViewById(R.id.doctorProfileTitleTxtViewId);
        totalPatient = findViewById(R.id.textView10);
        totalReview = findViewById(R.id.textView13);
        about = findViewById(R.id.aboutDoctorTxtId);

        allDoctorChamberRecyclerView = findViewById(R.id.doctorProfileChamberRecyclerId);
        doctorChamberListAdapter = new DoctorChamberAdapter(this);

        if (getIntent().hasExtra("doctorObj")) {
            doctor = getIntent().getParcelableExtra("doctorObj");
            assert doctor != null;
            doctorName.setText(doctor.getName());
            doctorQualification.setText(doctor.getEducation_history());
            about.setText(doctor.getAbout());

            new ApiCalls().getAllDoctorChambersByDoctorId(doctor.getId(), (doctorChambers, message) -> {
                if (doctorChambers != null) {
                    this.doctorChambers = doctorChambers;
                    doctorChamberListAdapter.setItems(doctorChambers);
                    doctorChamberListAdapter.setListener(this);
                    allDoctorChamberRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    allDoctorChamberRecyclerView.setAdapter(doctorChamberListAdapter);
                } else {
                    this.doctorChambers = new ArrayList<>();
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onItemClicked(DoctorChamber item, View view) {
        System.out.println(">>>>>>>>> doctor chamber name clicked");
    }

    public void bookAppointment(View view) {
        if (doctorChambers.isEmpty()) {
            return;
        }
        Intent intent = new Intent(this, DoctorAppointmentActivity.class);
        intent.putExtra("appointment", doctorChambers);
        startActivity(intent);
    }
}