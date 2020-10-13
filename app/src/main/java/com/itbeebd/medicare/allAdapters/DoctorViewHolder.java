package com.itbeebd.medicare.allAdapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.dataClasses.Doctor;

public class DoctorViewHolder extends BaseViewHolder<Doctor, OnRecyclerObjectClickListener<Doctor>> {
    private TextView textView;

    public DoctorViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.doctorNameTxtViewId);
    }

    @Override
    public void onBind(Doctor item, @Nullable OnRecyclerObjectClickListener<Doctor> listener) {
        textView.setText(item.getName());

        textView.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item);
        });
    }
}
