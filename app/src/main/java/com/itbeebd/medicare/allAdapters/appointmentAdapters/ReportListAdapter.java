package com.itbeebd.medicare.allAdapters.appointmentAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;

import java.util.Map;

public class ReportListAdapter extends GenericRecyclerAdapter<Map<String, String >, OnRecyclerObjectClickListener<Object>, ReportListViewHolder> {
    private final Context context;

    public ReportListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ReportListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReportListViewHolder(inflate(R.layout.single_report_file, parent), context);
    }
}