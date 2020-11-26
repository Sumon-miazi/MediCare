package com.itbeebd.medicare.pharmacy;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itbeebd.medicare.ChoseLocationMapActivity;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.api.PharmacyApi;
import com.itbeebd.medicare.bloodBank.BloodBankDashBoardActivity;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.utils.Pharmacy;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;

public class PharmacySignUpActivity extends AppCompatActivity {

        public static final int PICK_IMAGE = 1;
        private ImageView profileImage;
        private ImageView profileChangeBtn;
        private ImageView worldMapBtn;
        private TextInputEditText pharmacyName;
        private TextInputEditText pharmacyAddress;
        private TextInputEditText pharmacyAbout;
        private TextInputEditText pharmacyPhone;
        private TextInputEditText pharmacyEmail;
        private FirebaseUser firebaseUser;
        private Boolean imageSelectOrNot = false; // true means this intent for update.
        private Image imagePath;
        private Uri filePath;
        private String imageUrl = null;
        private Pharmacy pharmacy;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pharmacy_sign_up);
            pharmacy = new Pharmacy();

            initViews();

            FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
                if (firebaseAuth.getCurrentUser() != null) {
                    firebaseUser = firebaseAuth.getCurrentUser();
                    pharmacyPhone.setText(firebaseUser.getPhoneNumber());
                }
            });

            Glide.with(getApplicationContext())
                    .load(this.getResources().getString(R.string.lurem))
                    .into(profileImage);

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
            pharmacyName = findViewById(R.id.pharmacyNameId);
            pharmacyAddress = findViewById(R.id.pharmacyAddressId);
            pharmacyAbout = findViewById(R.id.pharmacyAboutId);
            pharmacyPhone = findViewById(R.id.pharmacyPhoneId);
            pharmacyEmail = findViewById(R.id.pharmacyEmailId);

            worldMapBtn.setOnClickListener(view -> {
                Intent intent = new Intent(this, ChoseLocationMapActivity.class);
                startActivityForResult(intent, 100);
            });
        }

        public void registerBloodBank(View view) {
            String name = pharmacyName.getText().toString();
            String address = pharmacyAddress.getText().toString();
            String about = pharmacyAbout.getText().toString();
            String number = pharmacyPhone.getText().toString();
            String email = pharmacyEmail.getText().toString();

            if(checkDataIsEmpty(name, address, about, number, email)){
                return;
            };
            if(pharmacy.getLat() == 0 ||  pharmacy.getLon() == 0){
                Toast.makeText(this,"Without choosing the right location you can't make the account", Toast.LENGTH_LONG).show();
                return;
            }

            pharmacy.setName(name);
            pharmacy.setAddress(address);
            pharmacy.setPhone(number);
            pharmacy.setAbout(about);
            pharmacy.setEmail(email);
            pharmacy.setUid(firebaseUser.getUid());
            pharmacy.setToken(CustomSharedPref.getInstance(this).getPushNotificationToken());
            pharmacy.setImage(imageUrl);

            new PharmacyApi().signUpPharmacy(pharmacy, (data, message) -> {
                if(data != null){
                    startActivity(new Intent(this, BloodBankDashBoardActivity.class));
                    finish();
                }
                else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            });

        }

        private boolean checkDataIsEmpty(String name, String address, String about, String number, String email){

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
                    imageUrl = image.getPath();
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
            else if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
                pharmacy.setLat(data.getDoubleExtra("latitude", 0));
                pharmacy.setLon(data.getDoubleExtra("longitude", 0));
            }
        }
}