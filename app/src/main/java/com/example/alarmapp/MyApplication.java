package com.example.alarmapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import io.realm.Realm;

public class MyApplication extends Application {
//    public static final String channel_id="AlarmApp";
//    CharSequence sequence="Sequence";


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());

//        createNotificationChannel();

    }

}
