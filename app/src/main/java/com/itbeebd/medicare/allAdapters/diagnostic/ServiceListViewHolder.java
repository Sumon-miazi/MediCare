package com.itbeebd.medicare.allAdapters.diagnostic;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;

public class ServiceListViewHolder extends BaseViewHolder<String, OnRecyclerObjectClickListener<String>> {
    private TextView name;
    private CheckBox checkBox;
    private Context context;

    public ServiceListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        name = itemView.findViewById(R.id.name);
        checkBox = itemView.findViewById(R.id.checkBox);
    }

    @Override
    public void onBind(String item, @Nullable OnRecyclerObjectClickListener<String> listener) {
        name.setText(item);
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            assert listener != null;
            listener.onItemClicked(item, compoundButton);
        });
    }
}
