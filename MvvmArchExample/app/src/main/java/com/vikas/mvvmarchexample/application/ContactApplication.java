package com.vikas.mvvmarchexample.application;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class ContactApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
