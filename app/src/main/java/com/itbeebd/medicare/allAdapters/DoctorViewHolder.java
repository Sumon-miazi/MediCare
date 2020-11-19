package com.itbeebd.medicare.allAdapters;

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
import com.itbeebd.medicare.utils.Doctor;
import com.mikhaellopez.circularimageview.CircularImageView;

public class DoctorViewHolder extends BaseViewHolder<Doctor, OnRecyclerObjectClickListener<Doctor>> {
    private TextView doctorName;
    private TextView doctorTitle;
    private TextView appointment;
    private ConstraintLayout doctorInfoLayout;
    private CircularImageView singleDoctorImageView;
    private Context context;
    private String imageBaseUrl = "";

    public DoctorViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        imageBaseUrl = ApiUrls.BASE_IMAGE_URL;
        doctorName = itemView.findViewById(R.id.doctorNameTxtViewId);
        doctorTitle = itemView.findViewById(R.id.doctorTitleTxtViewId);
        appointment = itemView.findViewById(R.id.doctorAppointmentTxtId);
        doctorInfoLayout = itemView.findViewById(R.id.doctorInfoLayoutId);
        singleDoctorImageView = itemView.findViewById(R.id.singleDoctorImageViewId);
    }

    @Override
    public void onBind(Doctor item, @Nullable OnRecyclerObjectClickListener<Doctor> listener) {
        doctorName.setText(item.getName());
        doctorTitle.setText(item.getEducationHistory());

        if(item.getImage() != null){
            System.out.println("image url>>>>>>>> " + imageBaseUrl + item.getImage());
            Glide.with(context)
                    .load(imageBaseUrl + item.getImage())
                    .into(singleDoctorImageView);
        }
        appointment.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

        doctorInfoLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}
