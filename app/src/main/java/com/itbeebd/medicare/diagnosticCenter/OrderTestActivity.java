package com.itbeebd.medicare.diagnosticCenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.diagnostic.ServiceListAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.DiagnosticCenterApi;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.TestReport;

import java.util.ArrayList;

public class OrderTestActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<String> {
    private RecyclerView testListView;
    private Button orderTestBtn;
    private ServiceListAdapter serviceListAdapter;
    private DiagnosticCenter diagnosticCenter;
    private ArrayList<String> services = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_test);

        serviceListAdapter = new ServiceListAdapter(this);

        testListView = findViewById(R.id.testListViewId);
        orderTestBtn = findViewById(R.id.orderTestBtnId);

        orderTestBtn.setOnClickListener(this::orderTest);

        if(getIntent().hasExtra("diagnosticCenterId")){
            diagnosticCenter = getIntent().getParcelableExtra("diagnosticCenter");
            if(diagnosticCenter != null) initRecyclerView();
        }
    }

    private void initRecyclerView(){
        serviceListAdapter.setItems(diagnosticCenter.getServices());
        serviceListAdapter.setListener(this);
        testListView.setLayoutManager(new LinearLayoutManager(this));
        testListView.setAdapter(serviceListAdapter);
    }

    private void orderTest(View view){
        if(diagnosticCenter == null) return;

        if(services.size() == 0){
            Toast.makeText(this, "Please a test first", Toast.LENGTH_LONG).show();
            return;
        }
        StringBuilder testName = new StringBuilder();
        for(int i = 0; i < services.size(); i++){
            testName.append(services.get(i)).append('\n');
        }

        TestReport testReport = new TestReport();
        testReport.setDiagnostic_center_id(diagnosticCenter.getDiagnosticId());
        testReport.setPatient_id(CustomSharedPref.getInstance(this).getUserId());
        testReport.setTitle(testName.toString());

        new DiagnosticCenterApi().orderTest(testReport, (status, message) -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemClicked(String item, View view) {
        if(services.contains(item)) services.remove(item);
        else services.add(item);
    }
}