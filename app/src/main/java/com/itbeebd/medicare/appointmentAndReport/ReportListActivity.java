package com.itbeebd.medicare.appointmentAndReport;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.appointmentAdapters.DiagnosticListAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.AppointmentApi;
import com.itbeebd.medicare.utils.ReportFile;

import java.util.HashMap;

public class ReportListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<Object> {

    private RecyclerView reportListView;
    private DiagnosticListAdapter diagnosticListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        reportListView = findViewById(R.id.reportListView);
        diagnosticListAdapter = new DiagnosticListAdapter(this);

        if(getIntent().hasExtra("appointment_id")){
            getAppointmentReport(getIntent().getIntExtra("appointment_id", 0));
        }

    }

    private void getAppointmentReport(int id){
        new AppointmentApi().getAnAppointmentReports(id, (data, message) -> {
            if(data != null){
                diagnosticListAdapter.setItems(data);
                diagnosticListAdapter.setListener(this);
                reportListView.setLayoutManager(new LinearLayoutManager(this));
                reportListView.setAdapter(diagnosticListAdapter);
            }
            else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemClicked(Object item, View view) {
        if(item instanceof ReportFile) System.out.println(">>>>>>>>>>. report header clicked");
        else if(item instanceof HashMap) System.out.println(">>>>>>>>>>. report file clicked");
    }
}