package com.itbeebd.medicare.allAdapters.blood;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.BloodDonor;

public class BloodDonorViewHolder extends BaseViewHolder<BloodDonor, OnRecyclerObjectClickListener<BloodDonor>> {
    private TextView bloodDonorName;
    private TextView bloodDonorAddress;
    private TextView lastBloodDonateDate;
    private TextView contactDonorTxt;
    private TextView bloodGroupNameTxtView;
    private ConstraintLayout bloodDonorInfoLayout;

    public BloodDonorViewHolder(@NonNull View itemView) {
        super(itemView);
        bloodDonorName = itemView.findViewById(R.id.bloodDonorNameTxtViewId);
        bloodDonorAddress = itemView.findViewById(R.id.donorAddressTxtViewId);
        lastBloodDonateDate = itemView.findViewById(R.id.lastBloodDonateDateId);
        contactDonorTxt = itemView.findViewById(R.id.contactDonorTxtId);
        bloodGroupNameTxtView = itemView.findViewById(R.id.bloodGroupNameTxtViewId);
    }

    @Override
    public void onBind(BloodDonor item, @Nullable OnRecyclerObjectClickListener<BloodDonor> listener) {
        bloodDonorName.setText(item.getName());
/*
        bloodBankInfoLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

 */
    }
}
