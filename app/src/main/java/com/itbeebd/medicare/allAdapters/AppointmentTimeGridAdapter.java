package com.itbeebd.medicare.allAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;

public class AppointmentTimeGridAdapter extends GenericRecyclerAdapter<String, OnRecyclerObjectClickListener<String>, AppointmentTimeGridViewHolder> {

    public AppointmentTimeGridAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public AppointmentTimeGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentTimeGridViewHolder(inflate(R.layout.single_time_view, parent));
    }
}
