package com.itbeebd.medicare.bloodBank;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.blood.BloodBankAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.BloodApi;
import com.itbeebd.medicare.utils.BloodBank;

public class BloodBankListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<BloodBank> {
    private RecyclerView bloodBankRecyclerView;
    private BloodBankAdapter bloodBankAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_list);

        bloodBankRecyclerView = findViewById(R.id.bloodBankRecyclerViewId);
        bloodBankAdapter = new BloodBankAdapter(this);

        setBloodBankRecyclerView();
    }

    private void setBloodBankRecyclerView() {
        new BloodApi().getAllBloodBank((bloodBanks, message) -> {
            if(bloodBanks != null){
                bloodBankAdapter.setItems(bloodBanks);
                bloodBankAdapter.setListener(this);
                bloodBankRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                bloodBankRecyclerView.setAdapter(bloodBankAdapter);
            }
            else Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemClicked(BloodBank item, View view) {

    }
}