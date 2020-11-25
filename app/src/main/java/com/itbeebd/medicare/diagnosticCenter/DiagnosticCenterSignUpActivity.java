package com.itbeebd.medicare.diagnosticCenter;

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
import com.itbeebd.medicare.api.DiagnosticCenterApi;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;

public class DiagnosticCenterSignUpActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    private ImageView profileImage;
    private ImageView profileChangeBtn;
    private ImageView worldMapBtn;
    private TextInputEditText dcName;
    private TextInputEditText dcAddress;
    private TextInputEditText dcServices;
    private TextInputEditText dcPhone;
    private TextInputEditText dcEmail;
    private FirebaseUser firebaseUser;
    private Boolean imageSelectOrNot = false; // true means this intent for update.
    private Image imagePath;
    private Uri filePath;
    private String imageUrl = null;
    private DiagnosticCenter diagnosticCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic_center_sign_up);
        diagnosticCenter = new DiagnosticCenter();
        initViews();

        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                firebaseUser = firebaseAuth.getCurrentUser();
                dcPhone.setText(firebaseUser.getPhoneNumber());
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
        dcName = findViewById(R.id.dcNameId);
        dcAddress = findViewById(R.id.dcAddressId);
        dcServices = findViewById(R.id.dcServicesId);
        dcPhone = findViewById(R.id.dcPhoneId);
        dcEmail = findViewById(R.id.dcEmailId);

        worldMapBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChoseLocationMapActivity.class);
            startActivityForResult(intent, 100);
        });
    }


    public void registerDiagnosticCenter(View view) {
        String name = dcName.getText().toString();
        String address = dcAddress.getText().toString();
        String services = dcServices.getText().toString();
        String number = dcPhone.getText().toString();
        String email = dcEmail.getText().toString();

        if(checkDataIsEmpty(name, address, services, number, email)){
            Toast.makeText(this,"Please fill up the all field", Toast.LENGTH_LONG).show();
            return;
        };
        if(diagnosticCenter.getLat() == 0 ||  diagnosticCenter.getLon() == 0){
            Toast.makeText(this,"Without choosing the right location you can't make the account", Toast.LENGTH_LONG).show();
            return;
        }

        diagnosticCenter.setName(name);
        diagnosticCenter.setAddress(address);
       // diagnosticCenter.setServices(services);
        diagnosticCenter.setEmail(email);
        diagnosticCenter.setPhone(number);
        diagnosticCenter.setUid(firebaseUser.getUid());
        diagnosticCenter.setToken(CustomSharedPref.getInstance(this).getPushNotificationToken());
        diagnosticCenter.setImage(imageUrl);

        new DiagnosticCenterApi().signUpDiagnosticCenter(diagnosticCenter, (data, message) -> {
            if(data != null){
                startActivity(new Intent(this, DiagnosticCenterDashBoardActivity.class));
                finish();
            }
            else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    private boolean checkDataIsEmpty(String name, String address, String services, String number, String email){

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
        if(services.isEmpty()){
            Toast.makeText(this, "write about the services", Toast.LENGTH_SHORT).show();
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
                imageUrl = image.getPath();
                System.out.println(">>>>>>>. filepath " + filePath);
                System.out.println(">>>>>>>. imageUrl " + imageUrl);

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
        else if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            diagnosticCenter.setLat(data.getDoubleExtra("latitude", 0));
            diagnosticCenter.setLon(data.getDoubleExtra("longitude", 0));
        }
    }
}

/*
            $table->foreignId('user_id')->constrained('users')->onDelete('cascade')->nullable();
            $table->string('name');
            $table->mediumText('services');
            $table->string('address');
            $table->string('phone');
            $table->decimal('lat', 10, 7);
            $table->decimal('long', 10, 7);
 */