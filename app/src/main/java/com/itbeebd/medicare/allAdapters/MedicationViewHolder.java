package com.itbeebd.medicare.allAdapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.Medication;

public class MedicationViewHolder extends BaseViewHolder<Medication, OnRecyclerObjectClickListener<Medication>> {
     private TextView medicineName;
     private TextView medicationTime;
     private TextView medicationCondition;
     private TextView medicationAmount;

    public MedicationViewHolder(@NonNull View itemView) {
        super(itemView);
        medicineName = itemView.findViewById(R.id.medicineNameId);
        medicationTime = itemView.findViewById(R.id.medicationTimeId);
        medicationCondition = itemView.findViewById(R.id.medicationConditionId);
        medicationAmount = itemView.findViewById(R.id.medicationAmountId);
    }

    @Override
    public void onBind(Medication item, @Nullable OnRecyclerObjectClickListener<Medication> listener) {
        medicineName.setText(item.getMedicineName());
        medicationTime.setText(item.getTime());
        medicationCondition.setText(item.getNote());
        medicationAmount.setText(item.getMedicationAmount());
    }
}