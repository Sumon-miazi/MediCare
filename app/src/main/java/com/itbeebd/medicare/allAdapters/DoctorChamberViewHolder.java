package com.itbeebd.medicare.allAdapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.DoctorChamber;

public class DoctorChamberViewHolder extends BaseViewHolder<DoctorChamber, OnRecyclerObjectClickListener<DoctorChamber>> {
    private TextView chamberName;
    private TextView chamberAddress;
    private CardView chamberCardView;

    public DoctorChamberViewHolder(@NonNull View itemView) {
        super(itemView);
        chamberName = itemView.findViewById(R.id.singleDoctorChamberNameId);
        chamberAddress = itemView.findViewById(R.id.singleDoctorChamberAddressId);
        chamberCardView = itemView.findViewById(R.id.doctorChamberCardViewId);
    }

    @Override
    public void onBind(DoctorChamber item, @Nullable OnRecyclerObjectClickListener<DoctorChamber> listener) {
        chamberName.setText(item.getName());
        chamberAddress.setText(item.getAddress());

        chamberCardView.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}
