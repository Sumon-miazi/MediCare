package com.itbeebd.medicare.userProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.flashbar.Flashbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.api.ApiCalls;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.utils.Patient;

public class UserSignUpActivity extends AppCompatActivity {

    private EditText patientName;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        patientName = findViewById(R.id.patientNameEditTxtId);

        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                firebaseUser = firebaseAuth.getCurrentUser();
            }
        });
    }

    public void signUpBtnClicked(View view) {
        String name = patientName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        Patient patientObj = new Patient(name);
        patientObj.setUid(firebaseUser.getUid());
        if (!CustomSharedPref.getInstance(this).getPushNotificationToken().equals(""))
            patientObj.setToken(CustomSharedPref.getInstance(this).getPushNotificationToken());

        new ApiCalls().signUpPatient(patientObj, (patient, message) -> {
            if (patient != null) {
                CustomSharedPref.getInstance(this).setUserSignedInOrNot(true);
                Intent intent = new Intent(this, UserProfileActivity.class);
                intent.putExtra("patient", patient);
                startActivity(intent);
                finish();
            }

        });
    }

    private Flashbar showFlash(String title, String message) {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title(title)
                .message(message)
                .backgroundColorRes(R.color.flash_bar)
                .duration(3000)
                .build();
    }
}