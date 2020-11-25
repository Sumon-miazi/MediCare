package com.itbeebd.medicare.bloodBank;

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
import com.itbeebd.medicare.api.BloodApi;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.utils.BloodBank;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;


public class BloodBankSignUpActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    private ImageView profileImage;
    private ImageView profileChangeBtn;
    private ImageView worldMapBtn;
    private TextInputEditText bbName;
    private TextInputEditText bbAddress;
    private TextInputEditText bbAbout;
    private TextInputEditText bbPhone;
    private TextInputEditText bbEmail;
    private FirebaseUser firebaseUser;
    private Boolean imageSelectOrNot = false; // true means this intent for update.
    private Image imagePath;
    private Uri filePath;
    private String imageUrl = null;
    private BloodBank bloodBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_sign_up);
        bloodBank = new BloodBank();
        initViews();

        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                firebaseUser = firebaseAuth.getCurrentUser();
                bbPhone.setText(firebaseUser.getPhoneNumber());
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
        bbName = findViewById(R.id.bbNameId);
        bbAddress = findViewById(R.id.bbAddressId);
        bbAbout = findViewById(R.id.bbAboutId);
        bbPhone = findViewById(R.id.bbPhoneId);
        bbEmail = findViewById(R.id.bbEmailId);

        worldMapBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChoseLocationMapActivity.class);
            startActivityForResult(intent, 100);
        });
    }

    public void registerBloodBank(View view) {
        String name = bbName.getText().toString();
        String address = bbAddress.getText().toString();
        String about = bbAbout.getText().toString();
        String number = bbPhone.getText().toString();
        String email = bbEmail.getText().toString();

        if(checkDataIsEmpty(name, address, about, number, email)){
            return;
        };
        if(bloodBank.getLat() == 0 ||  bloodBank.getLon() == 0){
            Toast.makeText(this,"Without choosing the right location you can't make the account", Toast.LENGTH_LONG).show();
            return;
        }

        bloodBank.setName(name);
        bloodBank.setAddress(address);
        bloodBank.setPhone(number);
        bloodBank.setAbout(about);
        bloodBank.setEmail(email);
        bloodBank.setUid(firebaseUser.getUid());
        bloodBank.setToken(CustomSharedPref.getInstance(this).getPushNotificationToken());
        bloodBank.setImage(imageUrl);

        new BloodApi().signUpBloodBank(bloodBank, (data, message) -> {
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
            bloodBank.setLat(data.getDoubleExtra("latitude", 0));
            bloodBank.setLon(data.getDoubleExtra("longitude", 0));
        }
    }

}
/*
            $table->string('name');
            $table->string('image')->nullable();
            $table->mediumText('about');
            $table->mediumText('address');
            $table->string('phone');
            $table->decimal('lat', 10, 7);
            $table->decimal('long', 10, 7);
 */