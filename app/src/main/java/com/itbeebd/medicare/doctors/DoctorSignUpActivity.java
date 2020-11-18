package com.itbeebd.medicare.doctors;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.api.ApiCalls;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.utils.Doctor;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;

public class DoctorSignUpActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    private ImageView profileImage;
    private ImageView profileChangeBtn;
    private ImageView worldMapBtn;
    private TextInputEditText dName;
    private TextInputEditText dAddress;
    private TextInputEditText dAbout;
    private TextInputEditText dPhone;
    private TextInputEditText dEmail;
    private TextInputEditText dReg;
    private TextInputEditText dEducationHistory;
    private TextInputEditText dSpecialist;
    private String gender = "male";
    private FirebaseUser firebaseUser;
    private Boolean imageSelectOrNot = false; // true means this intent for update.
    private Image imagePath;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);

        initViews();
        RadioGroup genderGroup = findViewById(R.id.genderId);

        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                firebaseUser = firebaseAuth.getCurrentUser();
                dPhone.setText(firebaseUser.getPhoneNumber());
            }
        });

        Glide.with(getApplicationContext())
                .load(this.getResources().getString(R.string.lurem))
                .into(profileImage);

        genderGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            System.out.println(">>>>>>>>> radioGroup " + i);
            if (radioGroup.getCheckedRadioButtonId() == R.id.genderMaleId) {
                gender = "male";
            } else {
                gender = "female";
            }
        });

        profileChangeBtn.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .setFolderMode(true)
                    .setFolderTitle("Album")
                    .setDirectoryName("Image Picker")
                    .setMultipleMode(false)
                    .setShowNumberIndicator(true)
                    .setMaxSize(1)
                    .setLimitMessage("You can select up to 1 images")
                    .setRequestCode(PICK_IMAGE)
                    .start();
        });
    }

    private void initViews(){
        profileImage = findViewById(R.id.profileImageId);
        profileChangeBtn = findViewById(R.id.profileChangeBtnId);
        worldMapBtn = findViewById(R.id.worldMapBtnId);
        dName = findViewById(R.id.dNameId);
        dAddress = findViewById(R.id.dAddressId);
        dAbout = findViewById(R.id.dAboutId);
        dPhone = findViewById(R.id.dPhoneId);
        dEmail = findViewById(R.id.dEmailId);
        dReg = findViewById(R.id.dRegId);
        dEducationHistory = findViewById(R.id.dEducationHistoryId);
        dSpecialist = findViewById(R.id.dSpecialistId);
    }

    public void registerDoctor(View view) {
        String name = dName.getText().toString();
        String address = dAddress.getText().toString();
        String about = dAbout.getText().toString();
        String number = dPhone.getText().toString();
        String email = dEmail.getText().toString();
        String regNo = dReg.getText().toString();
        String specialist = dSpecialist.getText().toString();
        String education = dEducationHistory.getText().toString();

        if(checkDataIsEmpty(name, address, about, number, email,regNo, specialist, education )){
            return;
        };

        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setAbout(about);
        doctor.setAddress(address);
        doctor.setPhone(number);
        doctor.setEmail(email);
        doctor.setBmdcRegNo(regNo);
        doctor.setSpecialist(specialist);
        doctor.setEducationHistory(education);
        doctor.setGender(gender);
        doctor.setUid(firebaseUser.getUid());
        doctor.setToken(CustomSharedPref.getInstance(this).getPushNotificationToken());

        new ApiCalls().signUpDoctor(doctor, (data, message) -> {
            if(data != null){
                startActivity(new Intent(this, DoctorDashBoardActivity.class));
                finish();
            }
            else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    private boolean checkDataIsEmpty(String name, String address, String about, String number, String email,
                                     String regNo, String specialist, String education){

        if(name.isEmpty()){
            Toast.makeText(this, "Enter blood bank name", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(address.isEmpty()){
            Toast.makeText(this, "Enter blood bank address", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(number.isEmpty()){
            Toast.makeText(this, "Enter blood bank number", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(about.isEmpty()){
            Toast.makeText(this, "write something about the blood bank", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(email.isEmpty()){
            Toast.makeText(this, "Email is mandatory", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(regNo.isEmpty()){
            Toast.makeText(this, "Reg no is mandatory", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(specialist.isEmpty()){
            Toast.makeText(this, "Put at least one speciality", Toast.LENGTH_SHORT).show();
            return true;
        }
        if(education.isEmpty()){
            Toast.makeText(this, "Education background is needed", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            // Do stuff with image's path or id. For example:
            //      imageOfTheContent.setVisibility(View.VISIBLE);
            //      chooseBtn.setVisibility(View.GONE);
            //      cancel_image_selection.setVisibility(View.VISIBLE);
            assert images != null;
            for (Image image : images) {
                filePath = Uri.fromFile(new File(image.getPath()));
                imageSelectOrNot = true;
                imagePath = image;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(image.getId()));
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .into(profileImage);
                } else {
                    Glide.with(getApplicationContext())
                            .load(image.getPath())
                            .into(profileImage);
                }
            }
        }
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