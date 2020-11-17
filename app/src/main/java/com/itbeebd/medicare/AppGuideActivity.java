package com.itbeebd.medicare;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroFragment;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.userProfile.UserSignInActivity;

import org.jetbrains.annotations.Nullable;

public class AppGuideActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_app_guide);
       // setColorTransitionsEnabled(true);
        /*
        addSlide(AppIntroFragment.newInstance(
                "The title of your slide",
                "A description that will be shown on the bottom",
                R.drawable.demo,
                Color.MAGENTA,
                Color.RED,
                Color.BLUE
                ));*/
       // addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.activity_dash_board));
        addSlide(AppIntroFragment.newInstance("welcome", "this is description"));
        addSlide(AppIntroFragment.newInstance("this is intro", "this is description"));
        addSlide(AppIntroFragment.newInstance("here more intro", "this is description"));
        addSlide(AppIntroFragment.newInstance("Let's get started", "this is description"));

        setVibrate(true);
        setVibrateDuration(50L);
        setImmersive(true);

        // Change Indicator Color
        setIndicatorColor(
                getResources().getColor(R.color.result_color),
                getResources().getColor(R.color.grey_bg_light)
        );
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        gotoSignInActivity();
    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        gotoSignInActivity();
    }

    private void gotoSignInActivity() {
        CustomSharedPref.getInstance(this).setAppIntroShownOrNot(true);
        startActivity(new Intent(this, UserSignInActivity.class));
        finish();
    }
}

/*

// Ask for required CAMERA permission on the second slide.
askForPermissions(
    permissions = arrayOf(Manifest.permission.CAMERA),
    slideNumber = 2,
    required = true)

// Ask for both optional ACCESS_FINE_LOCATION and WRITE_EXTERNAL_STORAGE
// permission on the third slide.
askForPermissions(
    permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ),
    slideNumber = 3,
    required = false)
 */