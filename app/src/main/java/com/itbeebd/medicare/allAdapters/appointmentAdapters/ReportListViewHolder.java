package com.itbeebd.medicare.allAdapters.appointmentAdapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.genericClasses.BaseViewHolder;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;

import java.util.Map;

public class ReportListViewHolder extends BaseViewHolder<Map<String, String >, OnRecyclerObjectClickListener<Object>> {
    private final TextView reportName;
    private final ImageView reportIcon;
    private final CardView layout;
    private final Context context;

    public ReportListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        layout = itemView.findViewById(R.id.cardView2);
        reportName = itemView.findViewById(R.id.textView5);
        reportIcon = itemView.findViewById(R.id.reportIconView);
        this.context = context;
    }

    @Override
    public void onBind( Map<String, String > item, @Nullable OnRecyclerObjectClickListener<Object> listener) {
        reportName.setText(item.get("title"));
        /*
        Glide.with(context)
                .load(context.getString(R.string.lurem))
                .into(reportIcon);

         */

        layout.setOnClickListener(view -> {
            //   timeBg.setBackgroundColor(context.getResources().getColor(R.color.green));
            //   timeTxt.setTextColor(context.getResources().getColor(R.color.white));
            assert listener != null;
            listener.onItemClicked(item, view);
        });

    }
}