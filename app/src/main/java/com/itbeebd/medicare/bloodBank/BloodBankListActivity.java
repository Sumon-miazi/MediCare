package com.itbeebd.medicare.bloodBank;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.MapsActivity;
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
        Intent intent = null;
        if(view.getId() == R.id.bbLocationBtn){
            intent = new Intent(this, MapsActivity.class);
            intent.putExtra("latitude", item.getLat());
            intent.putExtra("longitude", item.getLon());
            intent.putExtra("title", item.getName());
            startActivity(intent);
        }
        else if(view.getId() == R.id.bbCallBtn){
            dialPhoneNumber(item.getPhone());
        }

    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}