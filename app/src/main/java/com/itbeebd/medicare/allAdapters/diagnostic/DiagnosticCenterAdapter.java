package com.itbeebd.medicare.allAdapters.diagnostic;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.DiagnosticCenter;

public class DiagnosticCenterAdapter extends GenericRecyclerAdapter<DiagnosticCenter, OnRecyclerObjectClickListener<DiagnosticCenter>, DiagnosticCenterViewHolder> {
    private Context context;
    public DiagnosticCenterAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public DiagnosticCenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiagnosticCenterViewHolder(inflate(R.layout.single_diagnostic_center_view, parent), context);
    }
}