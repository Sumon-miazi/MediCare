package com.itbeebd.medicare.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.itbeebd.medicare.R;

public class CustomSharedPref {
    private static CustomSharedPref customSharedPref;
    private final SharedPreferences sharedPreferences;

    private CustomSharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.sharedPrefName), Context.MODE_PRIVATE);
    }  //private constructor.

    public static CustomSharedPref getInstance(Context context) {
        if (customSharedPref == null) {
            customSharedPref = new CustomSharedPref(context);
        }
        return customSharedPref;
    }



    public String getUserType() {
        return sharedPreferences.getString("UserType", "");
    }

    public void setUserType(String type) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserType", type);
        editor.apply();
    }



    public int getUserId() {
        return sharedPreferences.getInt("patient_id", 0);
    }

    public void setUserId(int id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("patient_id", id);
        editor.apply();
    }

    public boolean getUserSignedInOrNot() {
        return sharedPreferences.getBoolean("UserSignedInOrNot", false);
    }

    public void setUserSignedInOrNot(boolean flag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("UserSignedInOrNot", flag);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString("name", "Anonymous");
    }

    public void setUserName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    public String getUserUid() {
        return sharedPreferences.getString("uid", "");
    }

    public void setUserUid(String uid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid", uid);
        editor.apply();
    }

    public String getPushNotificationToken() {
        return sharedPreferences.getString("notificationToken", "");
    }

    public void setPushNotificationToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("notificationToken", token);
        editor.apply();
    }

    public boolean getPushNotificationTokenSyncOrNot() {
        return sharedPreferences.getBoolean("token_sync", false);
    }

    public void setPushNotificationTokenSyncOrNot(boolean token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("token_sync", token);
        editor.apply();
    }

    public String getNotice() {
        return sharedPreferences.getString("notice", "পরবর্তী পরীক্ষার তারিখ ও সিলেবাস খুব শীঘ্রই দেয়া হবে। আমাদের সাথেই থাকুন। ধন্যবাদ");
    }

    public void setNotice(String notice) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("notice", notice);
        editor.apply();
    }


    public String getNoticeKey() {
        return sharedPreferences.getString("notice_key", "");
    }

    public void setNoticeKey(String notice) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("notice_key", notice);
        editor.apply();
    }


    public boolean getAppIntroShownOrNot() {
        return sharedPreferences.getBoolean("AppIntroShownOrNot", false);
    }

    public void setAppIntroShownOrNot(boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("AppIntroShownOrNot", b);
        editor.apply();
    }
}