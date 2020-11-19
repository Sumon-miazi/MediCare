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
import com.itbeebd.medicare.utils.BloodRequest;
import com.mikhaellopez.circularimageview.CircularImageView;

public class BloodRequestViewHolder extends BaseViewHolder<BloodRequest, OnRecyclerObjectClickListener<BloodRequest>> {
    private CircularImageView userImageView;
    private TextView nameTxtView;
    private TextView bloodAmount;
    private TextView contact;
    private TextView bloodGroup;
    private Context context;

    public BloodRequestViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
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

        if(item.getImage() != null){
            System.out.println("<<<<<<<<<>>>>> " + ApiUrls.BASE_IMAGE_URL + item.getImage());
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getImage())
                    .into(userImageView);
        }
    }
}
