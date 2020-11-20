package com.itbeebd.medicare.utils;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;

public class DialCall extends Application {

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
