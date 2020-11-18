package com.itbeebd.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.medicare.bloodBank.BloodBankSignUpActivity;
import com.itbeebd.medicare.diagnosticCenter.DiagnosticCenterSignUpActivity;
import com.itbeebd.medicare.doctors.DoctorSignUpActivity;
import com.itbeebd.medicare.userProfile.UserSignUpActivity;

public class RegistrationOptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_option);
    }

    public void normalUser(View view) {
        startActivity(new Intent(this, UserSignUpActivity.class));
    }

    public void registerDoctor(View view) {
        startActivity(new Intent(this, DoctorSignUpActivity.class));
    }

    public void registerBloodBank(View view) {
        startActivity(new Intent(this, BloodBankSignUpActivity.class));
    }

    public void registerDiagnosticCenter(View view) {
        startActivity(new Intent(this, DiagnosticCenterSignUpActivity.class));
    }
}