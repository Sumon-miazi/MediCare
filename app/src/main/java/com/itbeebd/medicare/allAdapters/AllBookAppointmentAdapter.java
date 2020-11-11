package com.itbeebd.medicare.allAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;

public class AllBookAppointmentAdapter extends GenericRecyclerAdapter<String, OnRecyclerObjectClickListener<String>, AllBookAppointmentViewHolder> {
    private final Context context;

    public AllBookAppointmentAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AllBookAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllBookAppointmentViewHolder(inflate(R.layout.single_book_appointment_view, parent), context);
    }
}