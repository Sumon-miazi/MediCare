package com.itbeebd.medicare.allAdapters.doctor;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;

public class AppointmentTimeGridViewHolder extends BaseViewHolder<String, OnRecyclerObjectClickListener<String>> {
    private final TextView timeTxt;
    private final ConstraintLayout timeBg;
    private final Context context;

    public AppointmentTimeGridViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        timeTxt = itemView.findViewById(R.id.timeTxtId);
        timeBg = itemView.findViewById(R.id.timeBgId);
        this.context = context;
    }

    @Override
    public void onBind(String item, @Nullable OnRecyclerObjectClickListener<String> listener) {
        timeTxt.setText(item);

        timeTxt.setOnClickListener(view -> {
         //   timeBg.setBackgroundColor(context.getResources().getColor(R.color.green));
         //   timeTxt.setTextColor(context.getResources().getColor(R.color.white));
            Toast.makeText(context, item + " Selected", Toast.LENGTH_SHORT).show();
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}