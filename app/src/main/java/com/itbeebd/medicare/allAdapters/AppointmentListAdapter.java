package com.itbeebd.medicare.allAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.Appointment;

public class AppointmentListAdapter extends GenericRecyclerAdapter<Appointment, OnRecyclerObjectClickListener<Appointment>, AppointmentListViewHolder> {
    private final Context context;

    public AppointmentListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AppointmentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentListViewHolder(inflate(R.layout.single_book_appointment_view, parent), context);
    }
}