package com.itbeebd.medicare.doctors;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.medicare.R;

public class DoctorSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);
    }

    public void signUpBtnClicked(View view) {

    }
}

/*
            $table->string('name');
            $table->string('gender');
            $table->foreignId('hospital_id')->constrained('hospitals')->onDelete('cascade')->nullable();
            $table->string('about');
            $table->string('review')->nullable();
            $table->date('dob')->nullable();
            $table->longText('education_history');
            $table->mediumText('address');
            $table->string('phone');
 */