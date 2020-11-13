package com.itbeebd.medicare.bloodBank;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.blood.BloodBankAdapter;
import com.itbeebd.medicare.allAdapters.blood.BloodDonationRequestAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiCalls;
import com.itbeebd.medicare.api.Dao;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.Patient;

import java.util.ArrayList;


public class BloodBankFragment extends Fragment implements OnRecyclerObjectClickListener, View.OnClickListener {

    private TextView findDonorBtn;
    private TextView addReqBtn;
    private Button signUpBloodDonorBtn;
    private RecyclerView bloodBankRecyclerView;
    private RecyclerView recentBloodReqRecyclerView;
    private BloodBankAdapter bloodBankAdapter;
    private BloodDonationRequestAdapter bloodDonationRequestAdapter;

    public BloodBankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blood_bank, container, false);

        findDonorBtn = view.findViewById(R.id.findDonorBtnId);
        addReqBtn = view.findViewById(R.id.addReqBtnId);
        signUpBloodDonorBtn = view.findViewById(R.id.signUpBloodDonorBtnId);

        bloodBankRecyclerView = view.findViewById(R.id.bloodBankRecyclerViewId);
        recentBloodReqRecyclerView = view.findViewById(R.id.recentBloodReqRecyclerViewId);

        bloodBankAdapter = new BloodBankAdapter(getContext());
        bloodDonationRequestAdapter = new BloodDonationRequestAdapter(getContext());

        findDonorBtn.setOnClickListener(this);
        addReqBtn.setOnClickListener(this);
        signUpBloodDonorBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setBloodBankRecyclerView();
        setBloodDonationRequestAdapterRecyclerView();

        Patient patient = new Dao().getPatientDetails(CustomSharedPref.getInstance(getContext()).getUserId());

        if(patient != null && patient.getIs_blood_donor() != 1) signUpBloodDonorBtn.setVisibility(View.VISIBLE);
        else  signUpBloodDonorBtn.setVisibility(View.GONE);
    }

    private void setBloodBankRecyclerView() {
        ArrayList<BloodBank> bloodBanks = new ArrayList<>();
        bloodBanks.add(new BloodBank("Voluntary Blood Donation Program, Quantum Foundation", "31/v Shilpacharya Zainul Abedin Sarak, Shantinagar. 1217, Dhaka"));
        bloodBanks.add(new BloodBank("Rhythm Blood Bank", "Elephant Road, Hatirpool, Kancha Bazar, Dhaka 1205"));
        bloodBanks.add(new BloodBank("নিরাপদ ব্লাড ব্যাংক এন্ড ট্রান্সফিউশন সেন্টার", "3rd Floor 36 Green Road, Above Olive Restora Dhaka, 1205"));
        bloodBanks.add(new BloodBank("Oriental Blood Bank", "2B/30, SEL Green Centre, Green Road, Dhanmondi, Dhaka 1205"));

        bloodBankAdapter.setItems(bloodBanks);
        bloodBankAdapter.setListener(this);
        bloodBankRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        bloodBankRecyclerView.setAdapter(bloodBankAdapter);
    }

    private void setBloodDonationRequestAdapterRecyclerView() {
        /*
        ArrayList<BloodDonationRequest> bloodDonationRequests = new ArrayList<>();
        bloodDonationRequests.add(new BloodDonationRequest("Sumon miazi", "O+", "Cumilla Central Hospital", "+8801311205352"));
        bloodDonationRequests.add(new BloodDonationRequest("Al Hasan Arif", "A+", "LABAID, Green road, Dhanmondi", "+8801854678398"));
        bloodDonationRequests.add(new BloodDonationRequest("Mohian ul islam", "AB-", "Central Hospital, Dhanmondi", "+8801976405439"));
        bloodDonationRequests.add(new BloodDonationRequest("Abdullah Al Borhan", "B-", "Dhaka medical college and hospital", "+8801311206790"));
         */

        new ApiCalls().getBloodRequest((bloodDonationRequests, message) ->{
            bloodDonationRequestAdapter.setItems(bloodDonationRequests);
            bloodDonationRequestAdapter.setListener(this);
            recentBloodReqRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recentBloodReqRecyclerView.setAdapter(bloodDonationRequestAdapter);
        });

    }

    @Override
    public void onItemClicked(Object item, View view) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.findDonorBtnId) {
            startActivity(new Intent(getContext(), BloodDonorListActivity.class));
        } else if(view.getId() == R.id.addReqBtnId){
            startActivity(new Intent(getContext(), BloodRequestActivity.class));
        }
        else if(view.getId() == R.id.signUpBloodDonorBtnId){
            startActivity(new Intent(getContext(), BloodDonarRegistrationActivity.class));
        }
    }
}