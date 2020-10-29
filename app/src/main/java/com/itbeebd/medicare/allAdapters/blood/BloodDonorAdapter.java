package com.itbeebd.medicare.allAdapters.blood;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.BloodDonor;

public class BloodDonorAdapter extends GenericRecyclerAdapter<BloodDonor, OnRecyclerObjectClickListener<BloodDonor>, BloodDonorViewHolder> {

    public BloodDonorAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BloodDonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BloodDonorViewHolder(inflate(R.layout.single_blood_donor_view, parent));
    }
}
