package com.itbeebd.medicare.allAdapters.blood;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.BloodRequest;

public class BloodRequestAdapter extends GenericRecyclerAdapter<BloodRequest, OnRecyclerObjectClickListener<BloodRequest>, BloodRequestViewHolder> {
    private Context context;
    public BloodRequestAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public BloodRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BloodRequestViewHolder(inflate(R.layout.single_blood_request_view, parent), context);
    }
}