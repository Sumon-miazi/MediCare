package com.itbeebd.medicare.allAdapters.blood;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.BloodBank;

public class BloodBankAdapter extends GenericRecyclerAdapter<BloodBank, OnRecyclerObjectClickListener<BloodBank>, BloodBankViewHolder> {
    private Context context;
    public BloodBankAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public BloodBankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BloodBankViewHolder(inflate(R.layout.single_blood_bank_view, parent), context);
    }
}