package com.itbeebd.medicare.allAdapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.dataClasses.DoctorChamber;

public class DoctorChamberViewHolder extends BaseViewHolder<DoctorChamber, OnRecyclerObjectClickListener<DoctorChamber>> {
    private TextView textView;

    public DoctorChamberViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.singleDoctorChamberNameId);
    }

    @Override
    public void onBind(DoctorChamber item, @Nullable OnRecyclerObjectClickListener<DoctorChamber> listener) {
        //textView.setText(item.getName());

        textView.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item);
        });
    }
}
