package com.itbeebd.medicare.bloodBank;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.medicare.R;

public class BloodBankSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_sign_up);
    }
}

/*
            $table->string('name');
            $table->string('image')->nullable();
            $table->mediumText('about');
            $table->mediumText('address');
            $table->string('phone');
            $table->decimal('lat', 10, 7);
            $table->decimal('long', 10, 7);
 */