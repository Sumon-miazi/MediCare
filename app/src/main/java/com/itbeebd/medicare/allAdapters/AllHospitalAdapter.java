package com.itbeebd.medicare.allAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.dataClasses.Hospital;

public class AllHospitalAdapter extends GenericRecyclerAdapter<Hospital, OnRecyclerObjectClickListener<Hospital>, HospitalViewHolder> {

    public AllHospitalAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HospitalViewHolder(inflate(R.layout.single_hospital_view, parent));
    }


}
