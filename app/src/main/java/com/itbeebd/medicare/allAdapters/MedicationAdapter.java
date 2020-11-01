package com.itbeebd.medicare.allAdapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.Medication;

public class MedicationAdapter extends GenericRecyclerAdapter<Medication, OnRecyclerObjectClickListener<Medication>, MedicationViewHolder> {

    public MedicationAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicationViewHolder(inflate(R.layout.single_medication_item_view, parent));
    }

}
