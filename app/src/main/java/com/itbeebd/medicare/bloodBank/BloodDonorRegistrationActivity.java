package com.itbeebd.medicare.bloodBank;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.api.BloodApi;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.db.Dao;
import com.itbeebd.medicare.utils.Patient;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class BloodDonorRegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView lastDate;
    private CheckBox availableCheckBox;
    private Button submitBloodDonorRegBtn;
    private DatePickerDialog dpd;
    private boolean currentlyAvailable = true;
    private Date lastDonateDate = null;

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donar_registration);

        lastDate = findViewById(R.id.lastDateId);
        availableCheckBox = findViewById(R.id.availableCheckBoxId);
        submitBloodDonorRegBtn = findViewById(R.id.submitBloodDonorRegBtnId);

        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                BloodDonorRegistrationActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.setMaxDate(now);
        dpd.setTitle("Select last blood donation date");

        lastDate.setOnClickListener(view -> {
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });

        availableCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            currentlyAvailable = b;
        });

        submitBloodDonorRegBtn.setOnClickListener(view -> submitBloodDonorReg());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String d = dayOfMonth + "-" + (monthOfYear+1) + "-" + year;
        lastDonateDate = getDate(year, monthOfYear+1, dayOfMonth );
        lastDate.setText(d);
    }

    private void submitBloodDonorReg(){
        if(lastDonateDate == null){
            Toast.makeText(this,"Select your last blood donation date", Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println(">>>>>>.. last donate " + lastDonateDate);

        new BloodApi().addBloodDonor(CustomSharedPref.getInstance(this).getUserId(),
                lastDonateDate,
                currentlyAvailable,
                (status, message) -> {
            if(status){
                Patient patient = new Dao().getPatientDetails(CustomSharedPref.getInstance(this).getUserId());
                patient.setIs_blood_donor(1);
                patient.save();
                finish();
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                });
    }
}