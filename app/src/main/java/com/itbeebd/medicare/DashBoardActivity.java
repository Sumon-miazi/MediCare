package com.itbeebd.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mikhaellopez.circularimageview.CircularImageView;

public class DashBoardActivity extends AppCompatActivity {
    private CircularImageView main_doctorImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        main_doctorImageView = findViewById(R.id.main_doctorImageViewId);
    }
}