package com.itbeebd.medicare.allAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.dataClasses.DoctorChamber;

public class DoctorChamberAdapter extends GenericRecyclerAdapter<DoctorChamber, OnRecyclerObjectClickListener<DoctorChamber>, DoctorChamberViewHolder> {

    public DoctorChamberAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public DoctorChamberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorChamberViewHolder(inflate(R.layout.single_doctor_chamber_view, parent));
    }
}
