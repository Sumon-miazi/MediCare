package com.itbeebd.medicare.allAdapters.diagnostic;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;

public class ServiceListAdapter extends GenericRecyclerAdapter<String, OnRecyclerObjectClickListener<String>, ServiceListViewHolder> {
    private Context context;
    public ServiceListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceListViewHolder(inflate(R.layout.single_service_view, parent), context);
    }
}