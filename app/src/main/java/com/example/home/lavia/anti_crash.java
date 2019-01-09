package com.example.home.lavia;

import android.app.Application;
import android.content.Context;

public class anti_crash extends Application {

    public static anti_crash instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
    public static anti_crash getInstance() {
        return instance;
    }
}
