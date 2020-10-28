package com.itbeebd.medicare.allAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.Hospital;

public class HospitalAdapter extends GenericRecyclerAdapter<Hospital, OnRecyclerObjectClickListener<Hospital>, HospitalViewHolder> {

    public HospitalAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HospitalViewHolder(inflate(R.layout.single_hospital_view, parent));
    }


}
