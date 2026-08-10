package com.qashqai.cartool;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Apply the saved day/night theme before any activity starts.
        BaseActivity.applyGlobalNightMode(SharedPrefsHelper.getNightMode(this));
    }
}
