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
import com.itbeebd.medicare.utils.Specialist;
import com.mikhaellopez.circularimageview.CircularImageView;

public class SpecialistViewHolder extends BaseViewHolder<Specialist, OnRecyclerObjectClickListener<Specialist>> {
    private TextView name;
    private CircularImageView icon;
    private ConstraintLayout specialistLayout;
    private Context context;

    public SpecialistViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        name = itemView.findViewById(R.id.nameTxtViewId);
        icon = itemView.findViewById(R.id.imageViewId);
        specialistLayout = itemView.findViewById(R.id.specialistLayout);

    }

    @Override
    public void onBind(Specialist item, @Nullable OnRecyclerObjectClickListener<Specialist> listener) {
        name.setText(item.getName());
       // System.out.println(">>>>>>>><<<<<<" + item.getIcon());
        if(item.getIcon() != null){
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getIcon())
                    .into(icon);
        }
        specialistLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}