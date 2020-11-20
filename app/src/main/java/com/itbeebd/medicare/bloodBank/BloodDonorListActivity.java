package com.itbeebd.medicare.bloodBank;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.blood.BloodDonorAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.BloodApi;
import com.itbeebd.medicare.utils.BloodDonor;

public class BloodDonorListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<BloodDonor> {

    private RecyclerView allBloodDonorRecycler;
    private BloodDonorAdapter bloodDonorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donor_list);

        allBloodDonorRecycler = findViewById(R.id.bloodDonorListRecyclerViewId);
        bloodDonorAdapter = new BloodDonorAdapter(this);

        getBloodDonor("all");

/*
        ArrayList<BloodDonor> bloodDonors = new ArrayList<>();
        bloodDonors.add(new BloodDonor("Sumon miazi", "O+", "Cumilla, Sadar south"));
        bloodDonors.add(new BloodDonor("Al Hasan Arif", "AB+", "Ashulia, savar"));
        bloodDonors.add(new BloodDonor("Mohian Ul Islam", "A+", "Azimpur, Dhaka"));
        bloodDonors.add(new BloodDonor("Abdullah Al Borhan", "A-", "Dhaka"));

 */

    }

    private void getBloodDonor(String bloodGroup){
        new BloodApi().getBloodDonor(bloodGroup, (bloodDonors, message) -> {
            if(bloodDonors != null){
                bloodDonorAdapter.setItems(bloodDonors);
                bloodDonorAdapter.setListener(this);
                allBloodDonorRecycler.setLayoutManager(new LinearLayoutManager(this));
                allBloodDonorRecycler.setAdapter(bloodDonorAdapter);
            }
            else Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }

    public void sortBloodGroup(View view) {
        System.out.println(">>>>>>>> " + view.getId());
    }

    @Override
    public void onItemClicked(BloodDonor item, View view) {

    }
}