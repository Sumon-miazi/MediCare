package com.itbeebd.medicare.allAdapters.doctor;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.utils.DoctorChamber;
import com.mikhaellopez.circularimageview.CircularImageView;

public class DoctorChamberViewHolder extends BaseViewHolder<DoctorChamber, OnRecyclerObjectClickListener<DoctorChamber>> {
    private final TextView chamberName;
    private final TextView chamberAddress;
    private final CardView chamberCardView;
    private CircularImageView singleDoctorChamberImageView;
    private final Context context;

    public DoctorChamberViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        chamberName = itemView.findViewById(R.id.singleDoctorChamberNameId);
        chamberAddress = itemView.findViewById(R.id.singleDoctorChamberAddressId);
        chamberCardView = itemView.findViewById(R.id.doctorChamberCardViewId);
        singleDoctorChamberImageView = itemView.findViewById(R.id.singleDoctorChamberImageViewId);
    }

    @Override
    public void onBind(DoctorChamber item, @Nullable OnRecyclerObjectClickListener<DoctorChamber> listener) {
        chamberName.setText(item.getName());
        chamberAddress.setText(item.getAddress());

        if(item.getClicked() == 1) chamberCardView.setBackgroundColor(context.getResources().getColor(R.color.green));
        else chamberCardView.setBackgroundColor(context.getResources().getColor(R.color.white));

        if(item.getImage() != null){
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getImage())
                    .into(singleDoctorChamberImageView);
        }

        chamberCardView.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}
