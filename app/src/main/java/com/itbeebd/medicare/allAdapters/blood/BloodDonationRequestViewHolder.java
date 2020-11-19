package com.itbeebd.medicare.allAdapters.blood;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.utils.BloodDonationRequest;
import com.mikhaellopez.circularimageview.CircularImageView;

public class BloodDonationRequestViewHolder extends BaseViewHolder<BloodDonationRequest, OnRecyclerObjectClickListener<BloodDonationRequest>> {
    private TextView userName;
    private CircularImageView bloodDonorImageView;
    private TextView addressName;
    private TextView lastBloodDonateDate;
    private TextView contactNumber;
    private TextView bloodGroupName;
    private Context context;

    public BloodDonationRequestViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        bloodDonorImageView = itemView.findViewById(R.id.bloodDonorImageViewId);
        userName = itemView.findViewById(R.id.bloodDonorNameTxtViewId);
        addressName = itemView.findViewById(R.id.donorAddressTxtViewId);
        lastBloodDonateDate = itemView.findViewById(R.id.lastBloodDonateDateId);
        contactNumber = itemView.findViewById(R.id.contactDonorTxtId);
        bloodGroupName = itemView.findViewById(R.id.bloodGroupNameTxtViewId);
    }

    @Override
    public void onBind(BloodDonationRequest item, @Nullable OnRecyclerObjectClickListener<BloodDonationRequest> listener) {
        userName.setText(item.getName());
        //addressName.setText(item.getAddress());
        contactNumber.setText(item.getPhone());
        bloodGroupName.setText(item.getBloodGroup());

        if(item.getImage() != null){
            System.out.println("<<<<<<<<<>>>>> " + ApiUrls.BASE_IMAGE_URL + item.getImage());
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getImage())
                    .into(bloodDonorImageView);
        }
/*
        bloodBankInfoLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

 */
    }
}
