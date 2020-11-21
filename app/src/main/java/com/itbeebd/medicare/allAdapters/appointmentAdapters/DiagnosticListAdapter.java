package com.itbeebd.medicare.allAdapters.appointmentAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.ReportFile;

public class DiagnosticListAdapter  extends GenericRecyclerAdapter<ReportFile, OnRecyclerObjectClickListener<Object>, DiagnosticListViewHolder> {
    private final Context context;

    public DiagnosticListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public DiagnosticListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiagnosticListViewHolder(inflate(R.layout.single_dc_view_, parent), context);
    }
}