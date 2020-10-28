package com.itbeebd.medicare.allAdapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.Hospital;

public class HospitalViewHolder extends BaseViewHolder<Hospital, OnRecyclerObjectClickListener<Hospital>> {
    private TextView hospitalName;
    private ConstraintLayout hospitalInfoLayout;

    public HospitalViewHolder(@NonNull View itemView) {
        super(itemView);
        hospitalName = itemView.findViewById(R.id.hospitalNameTxtViewId);
        hospitalInfoLayout = itemView.findViewById(R.id.hospitalInfoLayoutId);
    }

    @Override
    public void onBind(Hospital item, @Nullable OnRecyclerObjectClickListener<Hospital> listener) {
        hospitalName.setText(item.getName());

        hospitalInfoLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}
