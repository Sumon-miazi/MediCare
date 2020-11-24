package com.itbeebd.medicare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.andrognito.flashbar.Flashbar;
import com.androidstudy.networkmanager.Tovuti;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itbeebd.medicare.api.BloodApi;
import com.itbeebd.medicare.api.DiagnosticCenterApi;
import com.itbeebd.medicare.api.DoctorApi;
import com.itbeebd.medicare.api.UserApi;
import com.itbeebd.medicare.bloodBank.BloodBankDashBoardActivity;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.diagnosticCenter.DiagnosticCenterDashBoardActivity;
import com.itbeebd.medicare.doctors.DoctorDashBoardActivity;
import com.itbeebd.medicare.userProfile.UserSignInActivity;
import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.Doctor;
import com.itbeebd.medicare.utils.Patient;


public class SplashActivity extends AppCompatActivity {
    String TAG = "splashActivity";
    private LottieAnimationView animationView;
    private TextView networkIndicate;
    private TextView splash_message;
    private FirebaseUser firebaseUser;
    private Flashbar flashbar;
    private boolean alreadyNotCalledMainActivity = true;
    private boolean alreadyNotCalledCheckUserExistOrNot = true;
    private boolean alreadyNotCalledSignInActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        splash_message = findViewById(R.id.splash_text);
        networkIndicate = findViewById(R.id.networkIndicateId);
        animationView = findViewById(R.id.route_animation);

        if(!CustomSharedPref.getInstance(this).getAppIntroShownOrNot()){
            startActivity(new Intent(this, AppGuideActivity.class));
            finish();
        }
    }

    private void goToActivityAsRequired() {
        System.out.println(">>>>>. splash goToActivityAsRequired");
        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser == null) {
                System.out.println(">>>>>. splash if");
                goToSignInActivity();
            } else {
                System.out.println(">>>>>. splash else");
                CustomSharedPref.getInstance(this).setUserUid(firebaseUser.getUid());
                goToMainActivity(firebaseUser, true);
            }
        });
    }

    private void goToSignInActivity() {
        //animationView.setRepeatCount(0);
        if(alreadyNotCalledSignInActivity){
            alreadyNotCalledSignInActivity = false;
            startActivity(new Intent(this, UserSignInActivity.class));
            finish();
        }
    }

    private void goToMainActivity(FirebaseUser user, boolean flag) {
        System.out.println("><<<<<<<<<" + CustomSharedPref.getInstance(this).getUserType());
        if (CustomSharedPref.getInstance(this).getUserSignedInOrNot() && flag) {
            if (alreadyNotCalledMainActivity) {
                System.out.println(">>>>>>>>>>>>>>>>> getUserSignedInOrNot true");
                alreadyNotCalledMainActivity = false;
                getUserDataAsUserType();
            }
        } else {
            if (alreadyNotCalledCheckUserExistOrNot) {
                System.out.println(">>>>>>>>>>>>>>>>> getUserSignedInOrNot false " + user.getUid());
                alreadyNotCalledCheckUserExistOrNot = false;
                new UserApi().checkUserExistOrNot(user.getUid(), (patient, doctor, bloodBank, diagnosticCenter, message, userType) -> {
                     System.out.println("checkUserExistOrNot>>>>>>>>>>>>>>>>>" + message + " " + userType);
                    if (userType != null) {
                        Intent intent = null;
                        switch (userType) {
                            case "patient":
                                intent = new Intent(this, MainActivity.class);
                                intent.putExtra("user_info", patient);
                                break;
                            case "doctor":
                                intent = new Intent(this, DoctorDashBoardActivity.class);
                                break;
                            case "bloodBank":
                                intent = new Intent(this, BloodBankDashBoardActivity.class);
                                break;
                            case "diagnosticCenter":
                                intent = new Intent(this, DiagnosticCenterDashBoardActivity.class);
                                break;
                        }
                        CustomSharedPref.getInstance(this).setUserType(userType);
                        CustomSharedPref.getInstance(this).setUserSignedInOrNot(true);
                        startActivity(intent);
                        finish();
                    } else if (message != null && message.equals("user not found")) {
                        startActivity(new Intent(this, RegistrationOptionActivity.class));
                        finish();
                    } else {
                        if (flashbar != null)
                            flashbar.dismiss();
                        flashbar = showFlash("Loading failed", "দুঃখিত এই মূহর্তে এপ্সটি ওপেন করা যাচ্ছে না। কিছুক্ষণ পর আবার চেষ্টা করুন। ধন্যবাদ ");
                        flashbar.show();
                    }
                  //  animationView.setRepeatCount(0);
                });
            }
        }
    }

    private void getUserDataAsUserType() {
        String userType = CustomSharedPref.getInstance(this).getUserType();

        switch (userType) {
            case "patient":
                new UserApi().getUserData(firebaseUser.getUid(), this::gotoDashBoardAsRequires);
                break;
            case "doctor":
                new DoctorApi().getDoctorData(firebaseUser.getUid(), this::gotoDashBoardAsRequires);
                break;
            case "bloodBank":
                new BloodApi().getBloodBankData(firebaseUser.getUid(), this::gotoDashBoardAsRequires);
                break;
            case "diagnosticCenter":
                new DiagnosticCenterApi().getDiagnosticCenterData(firebaseUser.getUid(), this::gotoDashBoardAsRequires);
                break;
            default:
                goToMainActivity(firebaseUser, false);
                break;
        }
    }

    private void gotoDashBoardAsRequires(Object object, String message){
        if (object != null) {
            Intent intent = null;
            if(object instanceof Patient){
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("user_info", (Patient)object);
            }
            else if(object instanceof Doctor){
                intent = new Intent(this, DoctorDashBoardActivity.class);
            }
            if(object instanceof BloodBank){
                intent = new Intent(this, BloodBankDashBoardActivity.class);
            }
            if(object instanceof DiagnosticCenter){
                intent = new Intent(this, DiagnosticCenterDashBoardActivity.class);
            }
            CustomSharedPref.getInstance(this).setUserSignedInOrNot(true);
            startActivity(intent);
            finish();

        } else if (message != null && message.equals("user not found")) {
            startActivity(new Intent(this, RegistrationOptionActivity.class));
            finish();
        } else {
            if (flashbar != null)
                flashbar.dismiss();
            flashbar = showFlash("Loading failed", "দুঃখিত এই মূহর্তে এপ্সটি ওপেন করা যাচ্ছে না। কিছুক্ষণ পর আবার চেষ্টা করুন। ধন্যবাদ ");
            flashbar.show();
        }
       // animationView.setRepeatCount(0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(CustomSharedPref.getInstance(this).getAppIntroShownOrNot())
        try {
            Tovuti.from(SplashActivity.this).monitor((connectionType, isConnected, isFast) -> {
                if (!isConnected) {
                    splash_message.setText(R.string.no_internet);
                    networkIndicate.setText(R.string.no_internet_error);
                    networkIndicate.setVisibility(View.VISIBLE);
                } else if (!isFast) {
                    splash_message.setText(R.string.slow_internet_connection);
                    networkIndicate.setText(R.string.slow_internet);
                    networkIndicate.setVisibility(View.VISIBLE);
                    goToActivityAsRequired();
                } else {
                    splash_message.setText(R.string.loading);
                    networkIndicate.setVisibility(View.GONE);
                    goToActivityAsRequired();
                }
            });
        } catch (Exception ignore) {
        }
    }

    @Override
    protected void onStop() {
        try {
            Tovuti.from(this).stop();
        } catch (Exception ignore) {
        }
        super.onStop();
    }

    private Flashbar showFlash(String title, String message) {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title(title)
                .message(message)
                .backgroundColorRes(R.color.flash_bar)
                .duration(6000)
                .build();
    }
}
