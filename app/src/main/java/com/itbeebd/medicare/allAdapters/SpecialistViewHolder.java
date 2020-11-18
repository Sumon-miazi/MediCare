package com.itbeebd.medicare.allAdapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.Specialist;
import com.mikhaellopez.circularimageview.CircularImageView;

public class SpecialistViewHolder extends BaseViewHolder<Specialist, OnRecyclerObjectClickListener<Specialist>> {
    private TextView name;
    private CircularImageView icon;
    private ConstraintLayout specialistLayout;

    public SpecialistViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.nameTxtViewId);
        icon = itemView.findViewById(R.id.imageViewId);
        specialistLayout = itemView.findViewById(R.id.specialistLayout);

    }

    @Override
    public void onBind(Specialist item, @Nullable OnRecyclerObjectClickListener<Specialist> listener) {
        name.setText(item.getName());

        specialistLayout.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}