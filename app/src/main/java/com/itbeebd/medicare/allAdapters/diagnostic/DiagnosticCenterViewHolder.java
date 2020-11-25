package com.itbeebd.medicare.allAdapters.diagnostic;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.mikhaellopez.circularimageview.CircularImageView;

import net.cachapa.expandablelayout.ExpandableLayout;

public class DiagnosticCenterViewHolder extends BaseViewHolder<DiagnosticCenter, OnRecyclerObjectClickListener<DiagnosticCenter>> {
    private TextView name;
    private Context context;
    private CircularImageView imageView;
    private ImageView showMeOnMap;
    private TextView phone;
    private TextView address;
    private TextView allServicesTxt;
    private Button seeAllServicesBtn,orderTest;
    private ExpandableLayout expandable_layout;

    public DiagnosticCenterViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        imageView = itemView.findViewById(R.id.main_userImageViewId);
        showMeOnMap = itemView.findViewById(R.id.showMeOnMapId);
        name = itemView.findViewById(R.id.textView5);
        address = itemView.findViewById(R.id.textView4);
        phone = itemView.findViewById(R.id.textView6);
        seeAllServicesBtn = itemView.findViewById(R.id.seeAllServicesBtnId);
        orderTest = itemView.findViewById(R.id.requestReportBtnId);
        expandable_layout = itemView.findViewById(R.id.expandable_layout);
        allServicesTxt = itemView.findViewById(R.id.allServicesTxtId);
        showMeOnMap.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBind(DiagnosticCenter item, @Nullable OnRecyclerObjectClickListener<DiagnosticCenter> listener) {
        name.setText(item.getName());
        address.setText(item.getAddress());
        phone.setText(item.getPhone());
        allServicesTxt.setText(Html.fromHtml(item.getServices()));

        if(item.getImage() != null){
            // System.out.println("<<<<<<<<<>>>>> " + ApiUrls.BASE_IMAGE_URL + item.getImage());
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getImage())
                    .into(imageView);
        }
        seeAllServicesBtn.setOnClickListener(view -> {
            expandable_layout.toggle();
        });

        orderTest.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

        showMeOnMap.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}
