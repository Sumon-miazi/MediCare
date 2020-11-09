package com.itbeebd.medicare.utils;

import android.app.Application;

import com.orm.SugarContext;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}