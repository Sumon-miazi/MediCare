package com.itbeebd.medicare.hospitals;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.AllHospitalAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiCalls;

public class HospitalListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener {

    private RecyclerView allHospitalRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        allHospitalRecyclerView = findViewById(R.id.allHospitalRecyclerViewId);

        new ApiCalls().getAllHospital(hospitals -> {
            if (hospitals != null) {
                AllHospitalAdapter hospitalAdapter = new AllHospitalAdapter(this);
                hospitalAdapter.setItems(hospitals);
                hospitalAdapter.setListener(this);
                allHospitalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                allHospitalRecyclerView.setAdapter(hospitalAdapter);
            }
        });
    }

    @Override
    public void onItemClicked(Object item) {
        System.out.println(">>>>>>>>>>. clicked");
    }
}