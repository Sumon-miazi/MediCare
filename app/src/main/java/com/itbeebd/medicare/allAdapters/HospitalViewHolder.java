package com.itbeebd.medicare.allAdapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.dataClasses.Hospital;

public class HospitalViewHolder extends BaseViewHolder<Hospital, OnRecyclerObjectClickListener<Hospital>> {

    public HospitalViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Hospital item, @Nullable OnRecyclerObjectClickListener<Hospital> listener) {

    }
}
