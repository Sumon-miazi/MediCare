package com.itbeebd.medicare.allAdapters.blood;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.utils.BloodDonor;
import com.mikhaellopez.circularimageview.CircularImageView;

public class BloodDonorViewHolder extends BaseViewHolder<BloodDonor, OnRecyclerObjectClickListener<BloodDonor>> {
    private TextView bloodDonorName;
    private TextView bloodDonorAddress;
    private TextView lastBloodDonateDate;
    private TextView contactDonorTxt;
    private TextView bloodGroupNameTxtView;
    private CircularImageView donorImage;
    private ConstraintLayout bloodDonorInfoLayout;
    private Context context;

    public BloodDonorViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        donorImage = itemView.findViewById(R.id.bloodDonorImageViewId);
        bloodDonorName = itemView.findViewById(R.id.bloodDonorNameTxtViewId);
        bloodDonorAddress = itemView.findViewById(R.id.donorAddressTxtViewId);
        lastBloodDonateDate = itemView.findViewById(R.id.lastBloodDonateDateId);
        contactDonorTxt = itemView.findViewById(R.id.contactDonorTxtId);
        bloodGroupNameTxtView = itemView.findViewById(R.id.bloodGroupNameTxtViewId);
    }

    @Override
    public void onBind(BloodDonor item, @Nullable OnRecyclerObjectClickListener<BloodDonor> listener) {
        bloodDonorName.setText(item.getName());
        bloodDonorAddress.setText(item.getAddress());
        lastBloodDonateDate.setText(item.getLastDonateDate());
        contactDonorTxt.setText(item.getPhone());
        bloodGroupNameTxtView.setText(item.getBloodGroup());

        if(item.getImage() != null){
            System.out.println("<<<<<<<<<>>>>> " + ApiUrls.BASE_IMAGE_URL + item.getImage());
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getImage())
                    .into(donorImage);
        }
/*
        bloodBankInfoLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
 */
    }
}
