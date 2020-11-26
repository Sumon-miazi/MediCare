package com.itbeebd.medicare.allAdapters.diagnostic;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.Pharmacy;

public class PharmacyAdapter extends GenericRecyclerAdapter<Pharmacy, OnRecyclerObjectClickListener<Pharmacy>, PharmacyViewHolder> {
    private Context context;
    public PharmacyAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public PharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PharmacyViewHolder(inflate(R.layout.single_blood_bank_view, parent), context);
    }
}