package com.itbeebd.medicare.allAdapters.blood;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.BloodDonationRequest;

public class BloodDonationRequestAdapter extends GenericRecyclerAdapter<BloodDonationRequest, OnRecyclerObjectClickListener<BloodDonationRequest>, BloodDonationRequestViewHolder> {
    private Context context;
    public BloodDonationRequestAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public BloodDonationRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BloodDonationRequestViewHolder(inflate(R.layout.single_blood_donor_view, parent), context);
    }
}
