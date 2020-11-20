package com.itbeebd.medicare.bloodBank;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.blood.BloodRequestAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.BloodApi;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.utils.BloodRequest;

public class BloodBankDashBoardActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<BloodRequest> {
    private RecyclerView recyclerView;
    private TextView textView;
    private BloodRequestAdapter bloodRequestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_dash_board);

        recyclerView = findViewById(R.id.bloodRequestOnBBId);
        textView = findViewById(R.id.noBloodReqTxtId);
        bloodRequestAdapter = new BloodRequestAdapter(this);

        new BloodApi().getAllBloodRequestOfABloodBankById(CustomSharedPref.getInstance(this).getUserId(),(bloodRequests, message) -> {
            if(bloodRequests != null){
                recyclerView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                bloodRequestAdapter.setItems(bloodRequests);
                bloodRequestAdapter.setListener(this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(bloodRequestAdapter);
            }
            else {
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClicked(BloodRequest item, View view) {

    }
}