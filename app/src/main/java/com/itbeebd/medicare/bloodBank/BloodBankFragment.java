package com.itbeebd.medicare.bloodBank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.itbeebd.medicare.R;


public class BloodBankFragment extends Fragment {

    private TextView findDonorBtn;
    private TextView addReqBtn;

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
        // findDonorBtn.setBackground(getResources().getDrawable(R.drawable.white_rounded_bg));
        // addReqBtn.setBackground(getResources().getDrawable(R.drawable.white_rounded_bg));
        return view;
    }
}