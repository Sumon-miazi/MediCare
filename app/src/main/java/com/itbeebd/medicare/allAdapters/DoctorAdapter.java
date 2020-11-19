package com.itbeebd.medicare.allAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.Doctor;

public class DoctorAdapter extends GenericRecyclerAdapter<Doctor, OnRecyclerObjectClickListener<Doctor>, DoctorViewHolder> {
    private Context context;
    public DoctorAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorViewHolder(inflate(R.layout.single_doctor_view, parent), this.context);
    }
}