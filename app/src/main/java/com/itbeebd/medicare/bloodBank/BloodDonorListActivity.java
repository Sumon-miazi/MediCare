package com.itbeebd.medicare.bloodBank;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.blood.BloodDonorAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.BloodDonor;

import java.util.ArrayList;

public class BloodDonorListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<BloodDonor> {

    private RecyclerView allBloodDonorRecycler;
    private BloodDonorAdapter bloodDonorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donor_list);

        allBloodDonorRecycler = findViewById(R.id.bloodDonorListRecyclerViewId);
        bloodDonorAdapter = new BloodDonorAdapter(this);

        ArrayList<BloodDonor> bloodDonors = new ArrayList<>();
        bloodDonors.add(new BloodDonor("Sumon miazi", "O+", "Cumilla, Sadar south"));
        bloodDonors.add(new BloodDonor("Al Hasan Arif", "AB+", "Ashulia, savar"));
        bloodDonors.add(new BloodDonor("Mohian Ul Islam", "A+", "Azimpur, Dhaka"));
        bloodDonors.add(new BloodDonor("Abdullah Al Borhan", "A-", "Dhaka"));


        bloodDonorAdapter.setItems(bloodDonors);
        bloodDonorAdapter.setListener(this);
        allBloodDonorRecycler.setLayoutManager(new LinearLayoutManager(this));
        allBloodDonorRecycler.setAdapter(bloodDonorAdapter);
    }

    public void sortBloodGroup(View view) {
        System.out.println(">>>>>>>> " + view.getId());
    }

    @Override
    public void onItemClicked(BloodDonor item, View view) {

    }
}