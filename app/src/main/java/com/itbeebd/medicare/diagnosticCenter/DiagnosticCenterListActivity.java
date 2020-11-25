package com.itbeebd.medicare.diagnosticCenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.MapsActivity;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.diagnostic.DiagnosticCenterAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.DiagnosticCenterApi;
import com.itbeebd.medicare.utils.DiagnosticCenter;

public class DiagnosticCenterListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<DiagnosticCenter> {
    private RecyclerView allDiagnosticCenterRecycler;
    private DiagnosticCenterAdapter diagnosticCenterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_center_list);

        allDiagnosticCenterRecycler = findViewById(R.id.allDiagnosticCenterRecyclerId);
        diagnosticCenterAdapter = new DiagnosticCenterAdapter(this);

        setUpData();
    }

    private void setUpData() {
        new DiagnosticCenterApi().getAllDiagnosticCenter((dataListFromApiCall, message) -> {
            if(dataListFromApiCall!= null){
                diagnosticCenterAdapter.setItems(dataListFromApiCall);
                diagnosticCenterAdapter.setListener(this);
                allDiagnosticCenterRecycler.setLayoutManager(new LinearLayoutManager(this));
                allDiagnosticCenterRecycler.setAdapter(diagnosticCenterAdapter);
            }
            else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemClicked(DiagnosticCenter item, View view) {
        Intent intent = null;
        if(view.getId() == R.id.requestReportBtnId){
            intent = new Intent(this, OrderTestActivity.class);
            intent.putExtra("diagnosticCenterId", item.getDiagnosticId());

        }
        else if(view.getId() == R.id.showMeOnMapId){
            intent = new Intent(this, MapsActivity.class);
            intent.putExtra("latitude", item.getLat());
            intent.putExtra("longitude", item.getLon());
            intent.putExtra("title", item.getName());
        }
        startActivity(intent);
    }
}