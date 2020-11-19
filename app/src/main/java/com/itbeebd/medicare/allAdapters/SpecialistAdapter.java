package com.itbeebd.medicare.allAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.Specialist;

public class SpecialistAdapter extends GenericRecyclerAdapter<Specialist, OnRecyclerObjectClickListener<Specialist>, SpecialistViewHolder> {
    private Context context;
    public SpecialistAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public SpecialistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpecialistViewHolder(inflate(R.layout.single_specialist_view, parent), context);
    }

}