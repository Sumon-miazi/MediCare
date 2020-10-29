package com.itbeebd.medicare.bloodBank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.blood.BloodBankAdapter;
import com.itbeebd.medicare.allAdapters.blood.BloodDonationRequestAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.BloodDonationRequest;

import java.util.ArrayList;


public class BloodBankFragment extends Fragment implements OnRecyclerObjectClickListener {

    private TextView findDonorBtn;
    private TextView addReqBtn;
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

        bloodBankRecyclerView = view.findViewById(R.id.bloodBankRecyclerViewId);
        recentBloodReqRecyclerView = view.findViewById(R.id.recentBloodReqRecyclerViewId);

        bloodBankAdapter = new BloodBankAdapter(getContext());
        bloodDonationRequestAdapter = new BloodDonationRequestAdapter(getContext());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setBloodBankRecyclerView();
        setBloodDonationRequestAdapterRecyclerView();
    }

    private void setBloodBankRecyclerView() {
        ArrayList<BloodBank> bloodBanks = new ArrayList<>();
        bloodBanks.add(new BloodBank("Ashar alo Blood Bank"));
        bloodBanks.add(new BloodBank("Ashar alo Blood Bank"));
        bloodBanks.add(new BloodBank("Ashar alo Blood Bank"));
        bloodBanks.add(new BloodBank("Ashar alo Blood Bank"));
        bloodBanks.add(new BloodBank("Ashar alo Blood Bank"));

        bloodBankAdapter.setItems(bloodBanks);
        bloodBankAdapter.setListener(this);
        bloodBankRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        bloodBankRecyclerView.setAdapter(bloodBankAdapter);
    }

    private void setBloodDonationRequestAdapterRecyclerView() {
        ArrayList<BloodDonationRequest> bloodDonationRequests = new ArrayList<>();
        bloodDonationRequests.add(new BloodDonationRequest("Sumon miazi", "O+"));
        bloodDonationRequests.add(new BloodDonationRequest("Sumon miazi", "O+"));
        bloodDonationRequests.add(new BloodDonationRequest("Sumon miazi", "O+"));
        bloodDonationRequests.add(new BloodDonationRequest("Sumon miazi", "O+"));
        bloodDonationRequests.add(new BloodDonationRequest("Sumon miazi", "O+"));
        bloodDonationRequests.add(new BloodDonationRequest("Sumon miazi", "O+"));

        bloodDonationRequestAdapter.setItems(bloodDonationRequests);
        bloodDonationRequestAdapter.setListener(this);
        recentBloodReqRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recentBloodReqRecyclerView.setAdapter(bloodDonationRequestAdapter);
    }

    @Override
    public void onItemClicked(Object item, View view) {

    }
}