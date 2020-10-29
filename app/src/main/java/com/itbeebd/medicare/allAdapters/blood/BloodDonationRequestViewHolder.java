package com.itbeebd.medicare.allAdapters.blood;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.BloodDonationRequest;

public class BloodDonationRequestViewHolder extends BaseViewHolder<BloodDonationRequest, OnRecyclerObjectClickListener<BloodDonationRequest>> {
    private TextView userName;
    private TextView addressName;
    private TextView lastBloodDonateDate;
    private TextView contactNumber;
    private TextView bloodGroupName;

    public BloodDonationRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.bloodDonorNameTxtViewId);
        addressName = itemView.findViewById(R.id.donorAddressTxtViewId);
        lastBloodDonateDate = itemView.findViewById(R.id.lastBloodDonateDateId);
        contactNumber = itemView.findViewById(R.id.contactDonorTxtId);
        bloodGroupName = itemView.findViewById(R.id.bloodGroupNameTxtViewId);
    }

    @Override
    public void onBind(BloodDonationRequest item, @Nullable OnRecyclerObjectClickListener<BloodDonationRequest> listener) {
        userName.setText(item.getName());
/*
        bloodBankInfoLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

 */
    }
}
