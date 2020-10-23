package com.itbeebd.medicare.allAdapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;

public class AppointmentTimeGridViewHolder extends BaseViewHolder<String, OnRecyclerObjectClickListener<String>> {
    private TextView timeTxt;

    public AppointmentTimeGridViewHolder(@NonNull View itemView) {
        super(itemView);
        timeTxt = itemView.findViewById(R.id.timeTxtId);
    }

    @Override
    public void onBind(String item, @Nullable OnRecyclerObjectClickListener<String> listener) {
        timeTxt.setText(item);

        timeTxt.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}