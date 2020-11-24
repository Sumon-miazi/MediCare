package com.itbeebd.medicare.allAdapters.blood;

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
import com.itbeebd.medicare.utils.BloodBank;
import com.mikhaellopez.circularimageview.CircularImageView;

public class BloodBankViewHolder extends BaseViewHolder<BloodBank, OnRecyclerObjectClickListener<BloodBank>> {
    private TextView bloodBankName;
    private TextView bloodBankAddress;
    private TextView bloodBankPhone;
    private Button bbCallBtn;
    private ImageView bbLocationBtn;
    private Button bbOrderBtn;
    private CircularImageView bloodBankImageView;
    private CardView bloodBankInfoLayout;
    private Context context;

    public BloodBankViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        bloodBankName = itemView.findViewById(R.id.bloodBankNameTxtViewId);
        bloodBankAddress = itemView.findViewById(R.id.bloodBankAddressTxtViewId);
        bloodBankPhone = itemView.findViewById(R.id.bloodBankPhoneTxtViewId);
        bbCallBtn = itemView.findViewById(R.id.bbCallBtn);
        bbLocationBtn = itemView.findViewById(R.id.bbLocationBtn);
        bbOrderBtn = itemView.findViewById(R.id.bbOrderBtn);
        bloodBankInfoLayout = itemView.findViewById(R.id.bloodBankLayoutId);
        bloodBankImageView = itemView.findViewById(R.id.bloodBankImageViewId);
    }

    @Override
    public void onBind(BloodBank item, @Nullable OnRecyclerObjectClickListener<BloodBank> listener) {
        bloodBankName.setText(item.getName());
        bloodBankAddress.setText(item.getAddress());
        bloodBankPhone.setText(item.getPhone());

        if(item.getImage() != null){
          //  System.out.println("<<<<<<<<<>>>>> " + ApiUrls.BASE_IMAGE_URL + item.getImage());
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getImage())
                    .into(bloodBankImageView);
        }

        bbLocationBtn.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

    }


}
