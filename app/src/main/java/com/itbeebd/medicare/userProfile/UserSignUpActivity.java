package com.itbeebd.medicare.userProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.api.ApiCalls;
import com.itbeebd.medicare.dataClasses.Patient;

public class UserSignUpActivity extends AppCompatActivity {

    private EditText patientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        patientName = findViewById(R.id.patientNameEditTxtId);
    }

    public void signUpBtnClicked(View view) {
        String name = patientName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        Patient patientObj = new Patient(name);

        new ApiCalls().signUpPatient(patientObj, (patient, message) -> {
            if (patient != null) {
                Intent intent = new Intent(this, UserProfileActivity.class);
                intent.putExtra("patient", patient);
                startActivity(intent);
                finish();
            }

        });
    }
}