package com.itbeebd.medicare.allAdapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.utils.Hospital;
import com.mikhaellopez.circularimageview.CircularImageView;

public class HospitalViewHolder extends BaseViewHolder<Hospital, OnRecyclerObjectClickListener<Hospital>> {
    private final TextView hospitalName;
    private final TextView hospitalAddress;
    private final CircularImageView hospitalImageView;
    private final CardView hospitalInfoLayout;
    private final ImageView hospitalMapIcon;
    private Context context;

    public HospitalViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        hospitalName = itemView.findViewById(R.id.hospitalNameTxtViewId);
        hospitalAddress = itemView.findViewById(R.id.hospitalAddressTxtViewId);
        hospitalImageView = itemView.findViewById(R.id.hospitalImageViewId);
        hospitalInfoLayout = itemView.findViewById(R.id.hospitalInfoLayoutId);
        hospitalMapIcon = itemView.findViewById(R.id.hospitalMapIconId);
    }

    @Override
    public void onBind(Hospital item, @Nullable OnRecyclerObjectClickListener<Hospital> listener) {
        hospitalName.setText(item.getName());
        hospitalAddress.setText(item.getAddress());

        if(item.getImage() != null){
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getImage())
                    .into(hospitalImageView);
        }

        hospitalInfoLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

        hospitalMapIcon.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}
