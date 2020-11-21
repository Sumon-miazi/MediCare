package com.itbeebd.medicare.allAdapters.appointmentAdapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.utils.ReportFile;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Map;

public class DiagnosticListViewHolder  extends BaseViewHolder<ReportFile, OnRecyclerObjectClickListener<Object>> implements OnRecyclerObjectClickListener<Object>{
    private final CircularImageView profileImage;
    private final TextView name;
    private final TextView address;
    private final TextView phoneTxt;
    private final RecyclerView reportListRecyclerView;
    private final Context context;
    private OnRecyclerObjectClickListener<Object> listener;

    public DiagnosticListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        profileImage = itemView.findViewById(R.id.main_doctorImageViewId);
        name = itemView.findViewById(R.id.textView5);
        address = itemView.findViewById(R.id.textView4);
        phoneTxt = itemView.findViewById(R.id.phoneTxt);
        reportListRecyclerView = itemView.findViewById(R.id.reportListRecyclerView);
        this.context = context;
    }

    @Override
    public void onBind(ReportFile item, @Nullable OnRecyclerObjectClickListener<Object> listener) {
        this.listener = listener;
        name.setText(item.getDiagnosticCenter().getName());
        address.setText(item.getDiagnosticCenter().getAddress());
        phoneTxt.setText(item.getDiagnosticCenter().getPhone());

        if(item.getDiagnosticCenter().getImage() != null){
            Glide.with(context)
                    .load(ApiUrls.BASE_IMAGE_URL + item.getDiagnosticCenter().getImage())
                    .into(profileImage);
        }

        setUpReportList(item.getFiles());
/*
        layout.setOnClickListener(view -> {
            //   timeBg.setBackgroundColor(context.getResources().getColor(R.color.green));
            //   timeTxt.setTextColor(context.getResources().getColor(R.color.white));
            assert listener != null;
            listener.onItemClicked(item, view);
        });
 */
    }

    private void setUpReportList(ArrayList<Map<String, String>> files){
        ReportListAdapter reportListAdapter = new ReportListAdapter(context);
        reportListAdapter.setItems(files);
        reportListAdapter.setListener(this);
        reportListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        reportListRecyclerView.setAdapter(reportListAdapter);

    }

    @Override
    public void onItemClicked(Object item, View view) {
        assert listener != null;
        listener.onItemClicked(item, view);
    }
}