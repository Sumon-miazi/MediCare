package com.itbeebd.medicare.allAdapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.dataClasses.Doctor;

public class DoctorViewHolder extends BaseViewHolder<Doctor, OnRecyclerObjectClickListener<Doctor>> {
    private TextView doctorName;
    private TextView appointment;
    private ConstraintLayout doctorInfoLayout;

    public DoctorViewHolder(@NonNull View itemView) {
        super(itemView);
        doctorName = itemView.findViewById(R.id.doctorNameTxtViewId);
        appointment = itemView.findViewById(R.id.doctorAppointmentTxtId);
        doctorInfoLayout = itemView.findViewById(R.id.doctorInfoLayoutId);
    }

    @Override
    public void onBind(Doctor item, @Nullable OnRecyclerObjectClickListener<Doctor> listener) {
        doctorName.setText(item.getName());

        appointment.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

        doctorInfoLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}
