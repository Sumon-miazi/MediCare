package com.itbeebd.medicare.allAdapters.blood;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.BloodBank;

public class BloodBankViewHolder extends BaseViewHolder<BloodBank, OnRecyclerObjectClickListener<BloodBank>> {
    private TextView bloodBankName;
    private TextView bloodBankAddress;
    private TextView bloodBankPhone;
    private Button bbCallBtn;
    private Button bbLocationBtn;
    private Button bbOrderBtn;
    private ConstraintLayout bloodBankInfoLayout;

    public BloodBankViewHolder(@NonNull View itemView) {
        super(itemView);
        bloodBankName = itemView.findViewById(R.id.bloodBankNameTxtViewId);
        bloodBankAddress = itemView.findViewById(R.id.bloodBankAddressTxtViewId);
        bloodBankPhone = itemView.findViewById(R.id.bloodBankPhoneTxtViewId);
        bbCallBtn = itemView.findViewById(R.id.bbCallBtn);
        bbLocationBtn = itemView.findViewById(R.id.bbLocationBtn);
        bbOrderBtn = itemView.findViewById(R.id.bbOrderBtn);
        bloodBankInfoLayout = itemView.findViewById(R.id.bloodBankLayoutId);
    }

    @Override
    public void onBind(BloodBank item, @Nullable OnRecyclerObjectClickListener<BloodBank> listener) {
        bloodBankName.setText(item.getName());
        bloodBankAddress.setText(item.getAddress());
        bloodBankPhone.setText(item.getPhone());
/*
        bloodBankInfoLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
         */
    }


}
