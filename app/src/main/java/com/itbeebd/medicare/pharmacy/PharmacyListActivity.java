package com.itbeebd.medicare.pharmacy;

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
import com.itbeebd.medicare.allAdapters.diagnostic.PharmacyAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.PharmacyApi;
import com.itbeebd.medicare.utils.Pharmacy;

public class PharmacyListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<Pharmacy> {

    private RecyclerView pharmacyRecyclerView;
    private PharmacyAdapter pharmacyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_list);

        pharmacyRecyclerView = findViewById(R.id.pharmacyRecyclerViewId);
        pharmacyAdapter = new PharmacyAdapter(this);

        setPharmacyRecyclerView();
    }

    private void setPharmacyRecyclerView() {
        new PharmacyApi().getAllPharmacy((pharmacies, message) -> {
            if(pharmacies != null){
                pharmacyAdapter.setItems(pharmacies);
                pharmacyAdapter.setListener(this);
                pharmacyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                pharmacyRecyclerView.setAdapter(pharmacyAdapter);
            }
            else Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemClicked(Pharmacy item, View view) {
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