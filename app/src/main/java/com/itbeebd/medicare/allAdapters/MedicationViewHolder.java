package com.itbeebd.medicare.allAdapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.Medication;

public class MedicationViewHolder extends BaseViewHolder<Medication, OnRecyclerObjectClickListener<Medication>> {
    // private TextView hospitalName;
    // private ConstraintLayout hospitalInfoLayout;

    public MedicationViewHolder(@NonNull View itemView) {
        super(itemView);
        //    hospitalName = itemView.findViewById(R.id.hospitalNameTxtViewId);
        //    hospitalInfoLayout = itemView.findViewById(R.id.hospitalInfoLayoutId);
    }

    @Override
    public void onBind(Medication item, @Nullable OnRecyclerObjectClickListener<Medication> listener) {
        //    hospitalName.setText(item.getName());

        //    hospitalInfoLayout.setOnClickListener(view -> {
        //         assert listener != null;
        //        listener.onItemClicked(item, view);
        //    });
    }
}