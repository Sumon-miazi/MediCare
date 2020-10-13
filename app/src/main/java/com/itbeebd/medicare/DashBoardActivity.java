package com.itbeebd.medicare;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.itbeebd.medicare.hospitals.HospitalListActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

public class DashBoardActivity extends AppCompatActivity {
    private CircularImageView main_doctorImageView;
    private CardView hospitalCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        main_doctorImageView = findViewById(R.id.main_doctorImageViewId);
        hospitalCardView = findViewById(R.id.hospitalCardViewId);

        hospitalCardView.setOnClickListener(view -> {
            startActivity(new Intent(this, HospitalListActivity.class));
        });
    }
}