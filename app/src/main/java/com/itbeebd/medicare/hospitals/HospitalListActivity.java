package com.itbeebd.medicare.hospitals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.MapsActivity;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.HospitalAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.HospitalApi;
import com.itbeebd.medicare.doctors.DoctorListActivity;
import com.itbeebd.medicare.utils.Hospital;

public class HospitalListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<Hospital> {

    private RecyclerView allHospitalRecyclerView;
    private HospitalAdapter hospitalListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        allHospitalRecyclerView = findViewById(R.id.allHospitalRecyclerViewId);
        hospitalListAdapter = new HospitalAdapter(this);

        new HospitalApi().getAllHospital((hospitals, message) -> {
            if (hospitals != null) {

                hospitalListAdapter.setItems(hospitals);
                hospitalListAdapter.setListener(this);
                allHospitalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                allHospitalRecyclerView.setAdapter(hospitalListAdapter);
            } else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemClicked(Hospital item, View view) {
        Intent intent = null;
        if(view.getId() == R.id.hospitalInfoLayoutId){
            intent = new Intent(this, DoctorListActivity.class);
            intent.putExtra("hospitalId", item.getId());
            intent.putExtra("source", "HospitalListActivity");

        }
        else if(view.getId() == R.id.hospitalMapIconId){
            intent = new Intent(this, MapsActivity.class);
            intent.putExtra("latitude", item.getLat());
            intent.putExtra("longitude", item.getLon());
            intent.putExtra("title", item.getName());
        }
        startActivity(intent);

    }
}