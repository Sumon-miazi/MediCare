package com.itbeebd.medicare.diagnosticCenter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.medicare.R;

public class DiagnosticCenterSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_center_sign_up);
    }
}

/*
            $table->foreignId('user_id')->constrained('users')->onDelete('cascade')->nullable();
            $table->string('name');
            $table->mediumText('services');
            $table->string('address');
            $table->string('phone');
            $table->decimal('lat', 10, 7);
            $table->decimal('long', 10, 7);
 */