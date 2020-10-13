package com.itbeebd.medicare.allAdapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.dataClasses.Hospital;

public class HospitalViewHolder extends BaseViewHolder<Hospital, OnRecyclerObjectClickListener<Hospital>> {
    private TextView textView;

    public HospitalViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.hospitalNameTxtViewId);
    }

    @Override
    public void onBind(Hospital item, @Nullable OnRecyclerObjectClickListener<Hospital> listener) {
        textView.setText(item.getName());

        textView.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item);
        });
    }
}
