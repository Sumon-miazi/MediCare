package com.itbeebd.medicare.diagnosticCenter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.api.DiagnosticCenterApi;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.utils.TestReport;

public class OrderTestActivity extends AppCompatActivity {
    private Spinner testSpinner;
    private Button orderTestBtn;
    private int diagnosticCenterId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_test);

        testSpinner = findViewById(R.id.testSpinnerId);
        orderTestBtn = findViewById(R.id.orderTestBtnId);

        initSpinner();

        orderTestBtn.setOnClickListener(this::orderTest);

        if(getIntent().hasExtra("diagnosticCenterId")){
            diagnosticCenterId = getIntent().getIntExtra("diagnosticCenterId", 0);
        }
    }

    private void initSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blood_for_list, R.layout.single_spinner_item);
        adapter.setDropDownViewResource(R.layout.single_spinner_item);
        testSpinner.setAdapter(adapter);
    }

    private void orderTest(View view){
        if(diagnosticCenterId == 0) return;

        String testName = testSpinner.getSelectedItem().toString();

        TestReport testReport = new TestReport();
        testReport.setDiagnostic_center_id(diagnosticCenterId);
        testReport.setPatient_id(CustomSharedPref.getInstance(this).getUserId());
        testReport.setTitle(testName);

        new DiagnosticCenterApi().orderTest(testReport, (status, message) -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }
}