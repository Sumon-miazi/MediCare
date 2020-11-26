package com.itbeebd.medicare.allAdapters.diagnostic;

import android.content.Context;
import android.view.View;
import android.widget.Button;
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
import com.itbeebd.medicare.utils.Pharmacy;
import com.mikhaellopez.circularimageview.CircularImageView;

public class PharmacyViewHolder extends BaseViewHolder<Pharmacy, OnRecyclerObjectClickListener<Pharmacy>> {
    private TextView pharmacyName;
    private TextView pharmacyAddress;
    private TextView pharmacyPhone;
    private Button pharmacyCallBtn;
    private ImageView pharmacyLocationBtn;
    private Button bbOrderBtn;
    private CircularImageView pharmacyImageView;
    private CardView pharmacyInfoLayout;
    private Context context;

    public PharmacyViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        pharmacyName = itemView.findViewById(R.id.bloodBankNameTxtViewId);
        pharmacyAddress = itemView.findViewById(R.id.bloodBankAddressTxtViewId);
        pharmacyPhone = itemView.findViewById(R.id.bloodBankPhoneTxtViewId);
        pharmacyCallBtn = itemView.findViewById(R.id.bbCallBtn);
        pharmacyLocationBtn = itemView.findViewById(R.id.bbLocationBtn);
        bbOrderBtn = itemView.findViewById(R.id.bbOrderBtn);
        pharmacyInfoLayout = itemView.findViewById(R.id.bloodBankLayoutId);
        pharmacyImageView = itemView.findViewById(R.id.bloodBankImageViewId);
    }

    @Override
    public void onBind(Pharmacy item, @Nullable OnRecyclerObjectClickListener<Pharmacy> listener) {
        pharmacyName.setText(item.getName());
        pharmacyAddress.setText(item.getAddress());
        pharmacyPhone.setText(item.getPhone());

        if(item.getImage() != null){
            //  System.out.println("<<<<<<<<<>>>>> " + ApiUrls.BASE_IMAGE_URL + item.getImage());
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getImage())
                    .into(pharmacyImageView);
        }

        pharmacyLocationBtn.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

        pharmacyCallBtn.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

    }


}
