package com.itbeebd.medicare.bloodBank;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.itbeebd.medicare.R;

public class BloodRequestActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);
        initCardViewAndTxtView();

        Spinner spinner = findViewById(R.id.bloodForSpinnerId);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blood_for_list, R.layout.single_spinner_item);
        adapter.setDropDownViewResource(R.layout.single_spinner_item);
        spinner.setAdapter(adapter);

        Spinner city_spinner = findViewById(R.id.citySpinnerId);
        ArrayAdapter<CharSequence> city_adapter = ArrayAdapter.createFromResource(this, R.array.blood_for_list, R.layout.single_spinner_item);
        city_adapter.setDropDownViewResource(R.layout.single_spinner_item);
        city_spinner.setAdapter(adapter);

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
}