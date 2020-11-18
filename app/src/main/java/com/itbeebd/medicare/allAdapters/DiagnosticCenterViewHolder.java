package com.itbeebd.medicare.allAdapters;

import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.mikhaellopez.circularimageview.CircularImageView;

import net.cachapa.expandablelayout.ExpandableLayout;

public class DiagnosticCenterViewHolder extends BaseViewHolder<DiagnosticCenter, OnRecyclerObjectClickListener<DiagnosticCenter>> {
    private TextView name;
    private CircularImageView imageView;
    private TextView phone;
    private TextView address;
    private TextView allServicesTxt;
    private Button seeAllServicesBtn,requestReportBtn;
    private ExpandableLayout expandable_layout;

    public DiagnosticCenterViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.main_userImageViewId);
        name = itemView.findViewById(R.id.textView5);
        address = itemView.findViewById(R.id.textView4);
        phone = itemView.findViewById(R.id.textView6);
        seeAllServicesBtn = itemView.findViewById(R.id.seeAllServicesBtnId);
        requestReportBtn = itemView.findViewById(R.id.requestReportBtnId);
        expandable_layout = itemView.findViewById(R.id.expandable_layout);
        allServicesTxt = itemView.findViewById(R.id.allServicesTxtId);
    }

    @Override
    public void onBind(DiagnosticCenter item, @Nullable OnRecyclerObjectClickListener<DiagnosticCenter> listener) {
        name.setText(item.getName());
        address.setText(item.getAddress());
        phone.setText(item.getPhone());
        allServicesTxt.setText(Html.fromHtml(item.getServices()));

        seeAllServicesBtn.setOnClickListener(view -> {
            expandable_layout.toggle();
        });

        requestReportBtn.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}
