package com.itbeebd.medicare.allAdapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.utils.Appointment;
import com.mikhaellopez.circularimageview.CircularImageView;

public class AppointmentListViewHolder extends BaseViewHolder<Appointment, OnRecyclerObjectClickListener<Appointment>> {
    private TextView appointmentDateTimeView;
    private TextView hospitalName;
    private TextView doctorName;
    private TextView doctorDegree;
    private TextView chamberAddress;
    private CircularImageView doctorImageView;
    private ImageView appointmentSettings;
    private final Context context;

    public AppointmentListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        appointmentDateTimeView = itemView.findViewById(R.id.appointmentDateTimeView);
        doctorImageView = itemView.findViewById(R.id.main_doctorImageViewId);
        doctorName = itemView.findViewById(R.id.textView5);
        doctorDegree = itemView.findViewById(R.id.textView4);
        hospitalName = itemView.findViewById(R.id.hospitalName);
        chamberAddress = itemView.findViewById(R.id.textView6);
        appointmentSettings = itemView.findViewById(R.id.imageView6);
        this.context = context;
    }

    @Override
    public void onBind(Appointment item, @Nullable OnRecyclerObjectClickListener<Appointment> listener) {
        doctorName.setText(item.getName());
        doctorDegree.setText(item.getDegree());
        hospitalName.setText(item.getHospitalName());
        chamberAddress.setText(item.getAddress());
        appointmentDateTimeView.setText(item.getAppointmentDateAndTimeString());

        if(item.getImage() != null){
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getImage())
                    .into(doctorImageView);
        }

        appointmentSettings.setOnClickListener(view -> {
            //   timeBg.setBackgroundColor(context.getResources().getColor(R.color.green));
            //   timeTxt.setTextColor(context.getResources().getColor(R.color.white));
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}