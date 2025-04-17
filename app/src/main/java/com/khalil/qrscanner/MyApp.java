package com.khalil.qrscanner;

import android.app.Application;

import com.google.firebase.crashlytics.FirebaseCrashlytics;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
    }
}