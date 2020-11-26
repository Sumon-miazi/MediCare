package com.itbeebd.medicare.doctors;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.doctor.DoctorChamberAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.api.DoctorApi;
import com.itbeebd.medicare.utils.Doctor;
import com.itbeebd.medicare.utils.DoctorChamber;

import java.util.ArrayList;

public class DoctorInfoActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<DoctorChamber> {

    private TextView doctorName;
    private TextView doctorQualification;
    private TextView totalPatient;
    private TextView totalReview;
    private ImageView doctorImage;
    private TextView about;
    private CardView doctorCallImg;

    private RecyclerView allDoctorChamberRecyclerView;
    private DoctorChamberAdapter doctorChamberListAdapter;
    private Doctor doctor;
    private ArrayList<DoctorChamber> doctorChambers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);

        doctorCallImg = findViewById(R.id.doctorCallImgId);
        doctorName = findViewById(R.id.doctorProfileNameTxtViewId);
        doctorImage = findViewById(R.id.main_doctorImageViewId);
        doctorQualification = findViewById(R.id.doctorProfileTitleTxtViewId);
        totalPatient = findViewById(R.id.textView10);
        totalReview = findViewById(R.id.textView13);
        about = findViewById(R.id.aboutDoctorTxtId);

        allDoctorChamberRecyclerView = findViewById(R.id.doctorProfileChamberRecyclerId);
        doctorChamberListAdapter = new DoctorChamberAdapter(this);

        if (getIntent().hasExtra("doctorObj")) {
            doctor = getIntent().getParcelableExtra("doctorObj");
            assert doctor != null;
            doctorName.setText(doctor.getName());
            doctorQualification.setText(doctor.getEducationHistory());
            about.setText(doctor.getAbout());

            if(doctor.getImage() != null){
                Glide.with(this)
                        .load(ApiUrls.BASE_IMAGE_URL + doctor.getImage())
                        .into(doctorImage);
            }

            new DoctorApi().getAllDoctorChambersByDoctorId(doctor.getDoctorId(), (doctorChambers, message) -> {
                if (doctorChambers != null) {
                    //this.doctor
                    this.doctorChambers = doctorChambers;
                    doctorChamberListAdapter.setItems(doctorChambers);
                    doctorChamberListAdapter.setListener(this);
                    allDoctorChamberRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    allDoctorChamberRecyclerView.setAdapter(doctorChamberListAdapter);
                } else {
                    this.doctorChambers = new ArrayList<>();
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }

        doctorCallImg.setOnClickListener(view -> {
            if(doctor != null){
                dialPhoneNumber(doctor.getPhone());
            }
        });
    }

    @Override
    public void onItemClicked(DoctorChamber item, View view) {
        System.out.println(">>>>>>>>> doctor chamber name clicked");
    }

    public void bookAppointment(View view) {
        System.out.println(">>>>>>>>>>>>. book appointment btn clicked");
        if (doctorChambers == null || doctorChambers.isEmpty()) {
            System.out.println(">>>>>>>>>>>>. doctor chamber is empty");
            return;
        }
        System.out.println(">>>>>>>>>>>>. doctor chamber size " + doctorChambers.size());
        Intent intent = new Intent(this, DoctorAppointmentActivity.class);
        intent.putExtra("appointment", doctorChambers);
        startActivity(intent);
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}