package com.itbeebd.medicare.allAdapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.dataClasses.DoctorChamber;

public class AppointmentTimeGridViewHolder extends BaseViewHolder<DoctorChamber, OnRecyclerObjectClickListener<DoctorChamber>> {

    public AppointmentTimeGridViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(DoctorChamber item, @Nullable OnRecyclerObjectClickListener<DoctorChamber> listener) {

    }
}