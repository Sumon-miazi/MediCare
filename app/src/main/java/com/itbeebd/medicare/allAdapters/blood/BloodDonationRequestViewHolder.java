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
    private CircularImageView userImageView;
    private TextView nameTxtView;
    private TextView bloodFor;
    private TextView cityName;
    private TextView hospitalName;
    private TextView needTime;
    private TextView bloodAmount;
    private TextView contact;
    private TextView bloodGroup;
    private Context context;

    public BloodDonationRequestViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        userImageView = itemView.findViewById(R.id.bloodDonorImageViewId);
        nameTxtView = itemView.findViewById(R.id.bloodDonorNameTxtViewId);
        bloodFor = itemView.findViewById(R.id.bloodForTxt);
        cityName = itemView.findViewById(R.id.cityNameTxt);
        hospitalName = itemView.findViewById(R.id.hospitalNameTxt);
        needTime = itemView.findViewById(R.id.needTimeTxt);
        bloodAmount = itemView.findViewById(R.id.amountTxt);
        contact = itemView.findViewById(R.id.contactTxtId);
        bloodGroup = itemView.findViewById(R.id.bloodGroupNameTxtViewId);
    }

    @Override
    public void onBind(BloodDonationRequest item, @Nullable OnRecyclerObjectClickListener<BloodDonationRequest> listener) {
        nameTxtView.setText(item.getName());
        bloodFor.setText(("Blood For: " + item.getBloodFor()));
        cityName.setText(("City : " + item.getCity()));
        hospitalName.setText(("Hospital : " + item.getHospital()));
        needTime.setText(("Time : " + item.getBloodNeededDateTime()));
        bloodAmount.setText(("Need : " + item.getAmount()));
        contact.setText(item.getPhone());
        bloodGroup.setText(item.getBloodGroup());

        if(item.getImage() != null){
            System.out.println("<<<<<<<<<>>>>> " + ApiUrls.BASE_IMAGE_URL + item.getImage());
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getImage())
                    .into(userImageView);
        }
/*
        bloodBankInfoLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

 */
    }
}
