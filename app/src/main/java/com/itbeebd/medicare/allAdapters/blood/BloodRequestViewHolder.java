package com.itbeebd.medicare.allAdapters.blood;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.BloodRequest;

public class BloodRequestViewHolder extends BaseViewHolder<BloodRequest, OnRecyclerObjectClickListener<BloodRequest>> {
    private ImageView userImageView;
    private TextView nameTxtView;
    private TextView bloodAmount;
    private TextView contact;
    private TextView bloodGroup;

    public BloodRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        userImageView = itemView.findViewById(R.id.bloodDonorImageViewId);
        nameTxtView = itemView.findViewById(R.id.bloodDonorNameTxtViewId);
        bloodAmount = itemView.findViewById(R.id.donorAddressTxtViewId);
        contact = itemView.findViewById(R.id.contactDonorTxtId);
        bloodGroup = itemView.findViewById(R.id.bloodGroupNameTxtViewId);

    }

    @Override
    public void onBind(BloodRequest item, @Nullable OnRecyclerObjectClickListener<BloodRequest> listener) {
        nameTxtView.setText(item.getName());
        bloodAmount.setText(item.getAmount());
        contact.setText(item.getPhone());
        bloodGroup.setText(item.getBloodGroup());
    }
}
