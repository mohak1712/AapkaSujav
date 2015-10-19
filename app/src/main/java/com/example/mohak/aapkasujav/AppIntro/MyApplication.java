package com.example.mohak.aapkasujav.AppIntro;

import android.app.Application;
import android.content.Context;

/**
 * Created by mohak on 22/8/15.
 */
public class MyApplication extends Application {
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();

    }


}