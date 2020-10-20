package com.itbeebd.medicare.doctors;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.DoctorChamberAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiCalls;
import com.itbeebd.medicare.dataClasses.DoctorChamber;

public class DoctorInfoActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<DoctorChamber> {

    private RecyclerView allDoctorChamberRecyclerView;
    private DoctorChamberAdapter doctorChamberListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);

        allDoctorChamberRecyclerView = findViewById(R.id.doctorProfileChamberRecyclerId);
        doctorChamberListAdapter = new DoctorChamberAdapter(this);

        if (getIntent().hasExtra("doctorId")) {
            int id = getIntent().getIntExtra("doctorId", 0);
            new ApiCalls().getAllDoctorChambersByDoctorId(id, (doctorChambers, message) -> {
                if (doctorChambers != null) {
                    doctorChamberListAdapter.setItems(doctorChambers);
                    doctorChamberListAdapter.setListener(this);
                    allDoctorChamberRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    allDoctorChamberRecyclerView.setAdapter(doctorChamberListAdapter);
                } else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onItemClicked(DoctorChamber item) {
        System.out.println(">>>>>>>>> doctor chamber name clicked");
    }
}