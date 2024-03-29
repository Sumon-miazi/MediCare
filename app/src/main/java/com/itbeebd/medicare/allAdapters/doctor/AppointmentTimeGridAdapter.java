package com.itbeebd.medicare.allAdapters.doctor;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;

public class AppointmentTimeGridAdapter extends GenericRecyclerAdapter<String, OnRecyclerObjectClickListener<String>, AppointmentTimeGridViewHolder> {
    private final Context context;
    public AppointmentTimeGridAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AppointmentTimeGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentTimeGridViewHolder(inflate(R.layout.single_time_view, parent), context);
    }
}
