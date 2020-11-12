package com.itbeebd.medicare.bloodBank;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.api.ApiCalls;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class BloodRequestActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

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

    private String bloodGroupName;
    private String bloodGroupFactor;
    private Spinner bloodForSpinner;
    private Spinner citySpinner;
    private Spinner amountSpinner;
    private TextInputEditText hospitalName;
    private Button whenNeededBloodBtn;
    private DatePickerDialog dpd;
    private TimePickerDialog tpd;
    private Date bloodNeededDateTime = null;
    private int year, month, day, hour = 0, minutes = 0;

    public static Date getDate(int year, int month, int day, int hour, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month + 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);
        initCardViewAndTxtView();

        bloodForSpinner = findViewById(R.id.bloodForSpinnerId);
        hospitalName = findViewById(R.id.hospitalNameId);
        citySpinner = findViewById(R.id.citySpinnerId);
        amountSpinner = findViewById(R.id.bloodAmountSpinnerId);
        whenNeededBloodBtn = findViewById(R.id.whenNeededBloodBtnId);

        Button addBloodRequestBtn = findViewById(R.id.addBloodRequestBtnId);

        setAllAdapter();
        setUpDatePicker();

        addBloodRequestBtn.setOnClickListener(this::addNewBloodRequest);
        whenNeededBloodBtn.setOnClickListener(this::showDataAndTimePicker);
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

    private void setAllAdapter(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blood_for_list, R.layout.single_spinner_item);
        adapter.setDropDownViewResource(R.layout.single_spinner_item);
        bloodForSpinner.setAdapter(adapter);


        ArrayAdapter<CharSequence> city_adapter = ArrayAdapter.createFromResource(this, R.array.blood_for_list, R.layout.single_spinner_item);
        city_adapter.setDropDownViewResource(R.layout.single_spinner_item);
        citySpinner.setAdapter(city_adapter);

        ArrayAdapter<CharSequence> amountAdapter = ArrayAdapter.createFromResource(this, R.array.blood_for_list, R.layout.single_spinner_item);
        amountAdapter.setDropDownViewResource(R.layout.single_spinner_item);
        amountSpinner.setAdapter(amountAdapter);
    }

    private void addNewBloodRequest(View view){
        String bloodFor = bloodForSpinner.getSelectedItem().toString();
        String city = citySpinner.getSelectedItem().toString();
        String hospital = hospitalName.getText().toString().trim();
        String amount = amountSpinner.getSelectedItem().toString();

        if(bloodNeededDateTime == null){
            Toast.makeText(this, "Select date and time first please", Toast.LENGTH_SHORT).show();
            return;
        }

        new ApiCalls().addNewBloodRequest(
                CustomSharedPref.getInstance(this).getUserId(),
                bloodFor,
                city,
                hospital,
                amount,
                bloodNeededDateTime,
                (bloodGroupName + bloodGroupFactor),
                (status, message) -> {
                if(status) finish();
                else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    private void showDataAndTimePicker(View view){
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
        bloodNeededDateTime = getDate(year, monthOfYear, dayOfMonth, this.hour, this.minutes);
        tpd.show(getSupportFragmentManager(),"Timepickerdialog");

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        this.hour = hourOfDay;
        this.minutes = minute;
        bloodNeededDateTime = getDate(year, month, day, hourOfDay, minute);
        System.out.println(">>>>>> time " + hourOfDay + " " + minute + " " + second);
    }

    private void setUpDatePicker(){

        tpd = TimePickerDialog.newInstance(BloodRequestActivity.this,false);

        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                BloodRequestActivity.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Initial day selection
        );
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH);
        day = now.get(Calendar.DAY_OF_MONTH);

        dpd.setMinDate(now);
    }
}

/*
[DatePickerDialog] setMinDate(Calendar day)
Set the minimum valid date to be selected. Date values before this date will be deactivated

[DatePickerDialog] setMaxDate(Calendar day)
Set the maximum valid date to be selected. Date values after this date will be deactivated

[TimePickerDialog] setMinTime(Timepoint time)
Set the minimum valid time to be selected. Time values earlier in the day will be deactivated

[TimePickerDialog] setMaxTime(Timepoint time)
Set the maximum valid time to be selected. Time values later in the day will be deactivated
 */