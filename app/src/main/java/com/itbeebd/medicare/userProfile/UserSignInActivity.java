package com.itbeebd.medicare.userProfile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.flashbar.Flashbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.itbeebd.medicare.MainActivity;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.RegistrationOptionActivity;
import com.itbeebd.medicare.api.ApiCalls;
import com.itbeebd.medicare.bloodBank.BloodBankDashBoardActivity;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.diagnosticCenter.DiagnosticCenterDashBoardActivity;
import com.itbeebd.medicare.doctors.DoctorDashBoardActivity;
import com.itbeebd.medicare.utils.CheckNetworkConnection;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class UserSignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final String TAG = "UserSignInActivity";
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private EditText userMobile;
    private Button verifyBtnId;
    private String mPhoneNumber;
    private Flashbar flashbar = null;
    private String verificationCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        mAuth = FirebaseAuth.getInstance();

        userMobile = findViewById(R.id.userNumberId);
        verifyBtnId = findViewById(R.id.verifyBtnId);

        verifyBtnId.setOnClickListener(this);
        
        // Initializing phone auth callbacks  (For verification, Not entering code yet, To get text send to device)
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // It will be invoked in two situations, i.e., instant verification and auto-retrieval:
                // 1 - In few of the cases, the phone number can be instantly verified without needing to  enter or send a verification code.
                // 2 - On some devices, Google Play services can automatically detect the incoming verification SMS and perform verification without
                //     user action.
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            //Creating onVerificationFailed() method.
            @Override
            public void onVerificationFailed(FirebaseException e) {
                // It is invoked when an invalid request is made for verification.
                // For instance, if the phone number format is not valid.
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // Setting error to text field
                    userMobile.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota has been exceeded for the project
                    // Toast.makeText(getApplicationContext(), "Quota exceeded", Toast.LENGTH_SHORT).show();
                    if (flashbar != null)
                        flashbar.dismiss();
                    flashbar = showFlash("দুঃখিত", "আপনার মোবাইল ভেরিফিকেশন এখন সম্ভব হচ্ছে না।  আপনি কিছুক্ষন পর আবার চেষ্টা করুন।  ধন্যবাদ ");
                    flashbar.show();
                } else if (e instanceof FirebaseNetworkException) {
                    if (flashbar != null)
                        flashbar.dismiss();
                    flashbar = showFlash("No internet", "আপনার ফোনের ইন্টারনেট কানেক্শনটি চেক করুন। এবং আবার চেষ্টা করুন।  ধন্যবাদ ");
                    flashbar.show();
                }
            }

            // Creating onCodeSent() method called after the verification code has been sent by SMS to the provided phone number.
            @Override
            public void onCodeSent(@NotNull String verificationId,
                                   @NotNull PhoneAuthProvider.ForceResendingToken token) {
                if (flashbar != null)
                    flashbar.dismiss();
                flashbar = showFlash("Attention Please", "আপনার ফোন নাম্বারে একটি ভেরিফিকেশন কোড পাঠানো হয়েছে।  যদি না যায় একটু অপেক্ষা করুন। কোডটি টাইপ করে SIGN IN বাটনের ক্লিক করুন।  ধন্যবাদ ");
                flashbar.show();

                showCodeConfirmationDialog();
                // The SMS verification code will be sent to the provided phone number
                // Now need to ask the user for entering the code and then construct a credential
                // through integrating the code with a verification ID.
                // Toast.makeText(UserSignInActivity.this, verificationId,Toast.LENGTH_SHORT).show();
                // Save the verification ID and resend token to use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        // [START_EXCLUDE]
        if (mVerificationInProgress) {
            verifyPhoneNumber();
        }
        // [END_EXCLUDE]
    }


    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }


    // Creating startPhoneNumberVerification() method
    //Getting text code sent. So we can use it to sign-in.
    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        mVerificationInProgress = true;
    }


    //Creating a helper method for verification of phone number with code.
    // Entering code and manually signing in with that code
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }


    // [START resend_verification]
    //Creating helper method for resending verification code.
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks

        if (flashbar != null)
            flashbar.dismiss();
        flashbar = showFlash("Code Resend", "আপনার ফোন নাম্বারে পুনরায় একটি ভেরিফিকেশন কোড পাঠানো হয়েছে।  যদি না যায় একটু অপেক্ষা করুন। কোডটি টাইপ করে SIGN IN বাটনের ক্লিক করুন।  ধন্যবাদ ");
        flashbar.show();
    }

    // Creating helper method signInWithPhoneAuthCredential().
    //Use text to sign-in.
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        //Sign-In is successful, update the UI with the signed-in user's information
                        //    Toast.makeText(UserSignInActivity.this, "signInWithCredential:success",Toast.LENGTH_SHORT).show();
                        boolean isNewUser = false;
                        try {
                            isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                        } catch (Exception ignored) {
                        }
                        //   Toast.makeText(UserSignInActivity.this, "signInWithCredential:success isNewUser",Toast.LENGTH_SHORT).show();
                        FirebaseUser user = task.getResult().getUser();
                        Intent intent;
                        if (isNewUser) {
                            //  Toast.makeText(UserSignInActivity.this, "Is New User!",Toast.LENGTH_SHORT).show();
                            intent = new Intent(this, RegistrationOptionActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if (user != null) {
                                gotoToDashBoardAsRequired(user);
                            } else {
                                intent = new Intent(this, RegistrationOptionActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                    } else {
                        // If the Sign-In fails, it will display a message and also update the UI
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            if (flashbar != null)
                                flashbar.dismiss();
                            flashbar = showFlash("Invalid code", "আপনি যে ভেরিফিকেশন কোড দিয়েছেন সেটি ভুল। সঠিক কোডটি টাইপ করে আবার চেষ্টা করুন। যদি কোডটি না পেয়ে থাকেন একটু অপেক্ষা করুন। স্লো নেটওয়ার্কের জন্য একটু সময় লাগতে পারে।  ধন্যবাদ ");
                            flashbar.show();
                        }
                    }
                });
        // progressDialogSignIn.dismiss();
    }

    private void gotoToDashBoardAsRequired(FirebaseUser user) {
        new ApiCalls().checkUserExistOrNot(user.getUid(), (patient, doctor, bloodBank, diagnosticCenter, message, userType) -> {
            System.out.println("checkUserExistOrNot>>>>>>>>>>>>>>>>>" + message);
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
        });
    }

    private void signOut() {
        mAuth.signOut();
    }

    @Override
    public void onClick(View v) {
        verifyPhoneNumber();
    }

    private void verifyPhoneNumber() {
        if (!new CheckNetworkConnection(this).haveNetworkConnection()) {
            // showAlertDialog(UserSignInActivity.this, "Connection error!!!", "Connect your device to the internet");
            if (flashbar != null)
                flashbar.dismiss();
            flashbar = showFlash("No Internet!", "আপনার ফোনের ইন্টারনেট কানেক্শনটি চেক করুন।  পুনরায় চেষ্টা করুন।  ধন্যবাদ");
            flashbar.show();
            return;
        }

        String phnNumber = userMobile.getText().toString();
        mPhoneNumber = phnNumber;

        if (phnNumber.isEmpty()) {
            if (flashbar != null)
                flashbar.dismiss();
            flashbar = showFlash("Attention Please", "আপনার ফোন নাম্বারটি দিয়ে তারপর next বাটনে ক্লিক করুন");
            flashbar.show();

            //    setErrorMessage(userMobile, "Enter number");
            return;
        }
        phnNumber = "+880" + phnNumber;
        if (!(Patterns.PHONE.matcher(phnNumber).matches())) {
            if (flashbar != null)
                flashbar.dismiss();
            flashbar = showFlash("Be careful", "আপনার দেয়া ফোন নাম্বারটি ভুল।  সঠিক নাম্বার দিয়ে পুনরায় চেষ্টা করুন।  ধন্যবাদ ");
            flashbar.show();
            //    userMobile.setError("Enter a valid phone number");
        }

        //Calling startPhoneNumberVerification helper method for verifying phone number.
        if (flashbar != null)
            flashbar.dismiss();
        flashbar = showFlash("Loading...", "একটু অপেক্ষা করুন। পরবর্তী করনীয় আপনাকে জানানো হবে।  ধন্যবাদ ");
        flashbar.show();
        startPhoneNumberVerification(phnNumber);
    }

    private void verifyVerificationCode() {
        if (!new CheckNetworkConnection(this).haveNetworkConnection()) {
            if (flashbar != null)
                flashbar.dismiss();
            flashbar = showFlash("Connection error", "আপনার ফোনের ইন্টারনেট কানেক্শনটি চেক করুন।  পুনরায় চেষ্টা করুন।  ধন্যবাদ");
            flashbar.show();
            //      showAlertDialog(UserSignInActivity.this, "Connection error!!!", "");
            return;
        }

        if (verificationCode.isEmpty()) {
            if (flashbar != null)
                flashbar.dismiss();
            flashbar = showFlash("Error!", "আপনাকে পাঠানো ভেরিফিকেশন কোডটি আগে দিন। যদি কোডটি না পেয়ে থাকেন তবে একটু অপেক্ষা করুন। ");
            flashbar.show();
            return;
        }

        if (verificationCode.length() < 6) {
            if (flashbar != null)
                flashbar.dismiss();
            flashbar = showFlash("Error!", "আপনাকে পাঠানো ভেরিফিকেশন কোডটি আগে দিন। যদি কোডটি না পেয়ে থাকেন তবে একটু অপেক্ষা করুন। ");
            flashbar.show();
            return;
        }
        if (flashbar != null)
            flashbar.dismiss();
        flashbar = showFlash("Loading...", "একটু অপেক্ষা করুন।  আপনার দেয়া কোডটি যাচাই করা হচ্ছে।  ধন্যবাদ ");
        flashbar.show();
        //Call the verifyPhoneNumberWithCode () method.
        verifyPhoneNumberWithCode(mVerificationId, verificationCode);
    }

    private void showCodeConfirmationDialog() {
        final Dialog dialog = new Dialog(UserSignInActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.verify_otp_view);

        //  Button enableNotiBtn = dialog.findViewById(R.id.enableNotiBtnId);
        ImageView dismissDialog = dialog.findViewById(R.id.closeVerificationDialogID);
        Button signInBtnId = dialog.findViewById(R.id.confirmOtpBtnId);
        TextView resendBtnId = dialog.findViewById(R.id.resendTxtBtnId);
        EditText verificationCodeID = dialog.findViewById(R.id.verificationCodeID);

        signInBtnId.setOnClickListener(v -> {
            verificationCode = verificationCodeID.getText().toString().trim();
            signInOrResendBtnClicked(true);
        });

        resendBtnId.setOnClickListener(v -> signInOrResendBtnClicked(false));
        dismissDialog.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void signInOrResendBtnClicked(boolean signInClicked) {
        if (signInClicked)
            verifyVerificationCode();
        else {
            if (flashbar != null)
                flashbar.dismiss();
            flashbar = showFlash("Code Resend", "একটু অপেক্ষা করুন।  পুনরায় ভেরিফিকেশন কোড পাঠানো হচ্ছে...");
            flashbar.show();
            //Call the resendVerificationCode () method.
            resendVerificationCode(mPhoneNumber, mResendToken);
        }
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



