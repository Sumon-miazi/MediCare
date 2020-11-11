package com.itbeebd.medicare.userProfile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.andrognito.flashbar.Flashbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itbeebd.medicare.MainActivity;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.api.ApiCalls;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.utils.Patient;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class UserSignUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextInputEditText patientName;
    private TextInputEditText patientAddress;
    private TextInputEditText patientPhone;
    private String gender = "male";
    private boolean signUpAsBloodDonor = false;
    private CheckBox signUpBloodDonor;
    private TextView lastBloodDonateDateText;

    private CardView cardView_a;
    private CardView cardView_b;
    private CardView cardView_ab;
    private CardView cardView_o;
    private CardView cardView_positive;
    private CardView cardView_negative;

    private TextView a_txt;
    private TextView b_txt;
    private TextView ab_txt;
    private TextView o_txt;
    private TextView positive_txt;
    private TextView negative_txt;

    private String bloodGroupName = "";
    private String bloodGroupFactor = "";

    private FirebaseUser firebaseUser;
    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        patientName = findViewById(R.id.patientNameEditTxtId);
        patientAddress = findViewById(R.id.patientAddressEditTxtId);
        patientPhone = findViewById(R.id.patientPhoneEditTxtId);
        RadioGroup genderGroup = findViewById(R.id.genderId);
        signUpBloodDonor = findViewById(R.id.signUpBloodDonorId);
        lastBloodDonateDateText = findViewById(R.id.lastBloodDonateDateTextId);

        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                UserSignUpActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.setTitle("Select last blood donation date");

        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                firebaseUser = firebaseAuth.getCurrentUser();
                patientPhone.setText(firebaseUser.getPhoneNumber());
            }
        });

        initCardViewAndTxtView();

        genderGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            System.out.println(">>>>>>>>> radioGroup " + i);
            if (radioGroup.getCheckedRadioButtonId() == R.id.genderMaleId) {
                gender = "male";
            } else {
                gender = "female";
            }
        });

        signUpBloodDonor.setOnCheckedChangeListener((compoundButton, b) -> {
            signUpAsBloodDonor = b;
            if (b) {
                if (bloodGroupName == null || bloodGroupFactor == null) {
                    Toast.makeText(this, "Please select your blood group first", Toast.LENGTH_SHORT).show();
                    signUpBloodDonor.setChecked(false);
                }
                lastBloodDonateDateText.setVisibility(View.VISIBLE);
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            } else lastBloodDonateDateText.setVisibility(View.GONE);
        });

        lastBloodDonateDateText.setOnClickListener(view -> dpd.show(getSupportFragmentManager(), "Datepickerdialog"));
    }

    private void initCardViewAndTxtView() {
        cardView_a = findViewById(R.id.a_bloodId);
        cardView_b = findViewById(R.id.b_bloodId);
        cardView_ab = findViewById(R.id.ab_bloodId);
        cardView_o = findViewById(R.id.o_bloodId);
        cardView_positive = findViewById(R.id.positive_bloodId);
        cardView_negative = findViewById(R.id.negative_bloodId);

        a_txt = findViewById(R.id.a_txt_bloodId);
        b_txt = findViewById(R.id.b_txt_bloodId);
        ab_txt = findViewById(R.id.ab_txt_bloodId);
        o_txt = findViewById(R.id.o_txt_bloodId);
        positive_txt = findViewById(R.id.positive_txt_bloodId);
        negative_txt = findViewById(R.id.negative_txt_bloodId);
    }

    public void bloodGroupNameClicked(View view) {

        switch (view.getId()) {
            case R.id.a_bloodId:
                setColorAndBackgroundOfCardView();
                setColorAndBackgroundOfCardView(cardView_a, a_txt);
                bloodGroupName = a_txt.getText().toString();
                break;


            case R.id.b_bloodId:
                setColorAndBackgroundOfCardView();
                setColorAndBackgroundOfCardView(cardView_b, b_txt);
                bloodGroupName = b_txt.getText().toString();
                break;

            case R.id.ab_bloodId:
                setColorAndBackgroundOfCardView();
                setColorAndBackgroundOfCardView(cardView_ab, ab_txt);
                bloodGroupName = ab_txt.getText().toString();
                break;

            case R.id.o_bloodId:
                setColorAndBackgroundOfCardView();
                setColorAndBackgroundOfCardView(cardView_o, o_txt);
                bloodGroupName = o_txt.getText().toString();
                break;

            case R.id.positive_bloodId:
                changePositiveAndNegativeCardViewColor(cardView_positive, cardView_negative, positive_txt, negative_txt);
                bloodGroupFactor = positive_txt.getText().toString();
                break;

            case R.id.negative_bloodId:
                changePositiveAndNegativeCardViewColor(cardView_negative, cardView_positive, negative_txt, positive_txt);
                bloodGroupFactor = negative_txt.getText().toString();
                break;

        }
        System.out.println(">>>>>> blood " + bloodGroupName);
        System.out.println(">>>>>> factor " + bloodGroupFactor);
    }

    private void setColorAndBackgroundOfCardView() {
        cardView_a.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView_b.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView_ab.setCardBackgroundColor(getResources().getColor(R.color.white));
        cardView_o.setCardBackgroundColor(getResources().getColor(R.color.white));

        a_txt.setTextColor(getResources().getColor(R.color.pink_700));
        b_txt.setTextColor(getResources().getColor(R.color.pink_700));
        ab_txt.setTextColor(getResources().getColor(R.color.pink_700));
        o_txt.setTextColor(getResources().getColor(R.color.pink_700));
    }

    private void setColorAndBackgroundOfCardView(CardView cardView, TextView textView) {
        cardView.setCardBackgroundColor(getResources().getColor(R.color.pink_700));
        textView.setTextColor(getResources().getColor(R.color.white));
    }

    private void changePositiveAndNegativeCardViewColor(CardView one, CardView two, TextView one_txt, TextView two_txt) {
        one.setCardBackgroundColor(getResources().getColor(R.color.pink_700));
        one_txt.setTextColor(getResources().getColor(R.color.white));

        two.setCardBackgroundColor(getResources().getColor(R.color.white));
        two_txt.setTextColor(getResources().getColor(R.color.pink_700));
    }

    public void signUpBtnClicked(View view) {
        String name = patientName.getText().toString();
        String address = patientAddress.getText().toString();
        String phone = patientPhone.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (address.isEmpty()) {
            Toast.makeText(this, "Enter your address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.isEmpty()) {
            Toast.makeText(this, "Enter your phone", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bloodGroupName.isEmpty()) {
            Toast.makeText(this, "Select your blood group", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bloodGroupFactor.isEmpty()) {
            Toast.makeText(this, "Select your blood type", Toast.LENGTH_SHORT).show();
            return;
        }

        Patient patientObj = new Patient();
        patientObj.setName(name);
        patientObj.setUid(firebaseUser.getUid());
        patientObj.setAddress(address);
        patientObj.setPhone(phone);
        patientObj.setBlood_group(bloodGroupName + bloodGroupFactor);
        patientObj.setGender(gender);
        patientObj.setIs_blood_donor(signUpAsBloodDonor ? 1 : 0);
        patientObj.setToken(CustomSharedPref.getInstance(this).getPushNotificationToken());


        new ApiCalls(this).signUpPatient(patientObj, (patient, message) -> {
            if (patient != null) {
                // Intent intent = new Intent(this, UserProfileActivity.class);
                Intent intent = new Intent(this, MainActivity.class);
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "Last blood donation date: <b>" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year + "</b>";
        lastBloodDonateDateText.setText(Html.fromHtml(date));
    }
}