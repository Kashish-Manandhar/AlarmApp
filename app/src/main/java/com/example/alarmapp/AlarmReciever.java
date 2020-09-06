package com.example.alarmapp;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;



public class AlarmReciever extends BroadcastReceiver {

    Realm realm = Realm.getDefaultInstance();
    public static Ringtone ringtone;
//    int count = 0;
    int id;
    AlarmDB alarmDB;

//    static Ringtone ringtone;
    @Override
    public void onReceive(Context context, Intent intent) {
        id = intent.getExtras().getInt("alarm_id");


        alarmDB = realm.where(AlarmDB.class).equalTo("alarm_id", id).findFirst();

        Uri alarm_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarm_uri == null) {
            alarm_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(context, alarm_uri);
        Intent serviceIntent=new Intent(context,AlarmService.class);
        serviceIntent.putExtra("alarm_id",id);


        if (alarmDB.isStatus()) {

            if (alarmDB.isRepeat()) {
                Log.d("Alarm Triggerred", "onReceive: " + alarmDB.isSun());
                Log.d("TOday Check", "" + check_today(alarmDB));


                if (check_today(this.alarmDB)) {

                    ringtone.play();


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context.startForegroundService(serviceIntent);
                        } else {
                            context.startService(serviceIntent);
                        }

//
                }
                else {
                    //do something
                    Log.d("Something","Not this");
                }

            }
            else {
                ringtone.play();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(serviceIntent);
                    } else {
                        context.startService(serviceIntent);
                    }

            }

        }

        else
        {
            Log.d("Alarm","Status is off");
        }


    }


    private boolean check_today(AlarmDB alarmDB) {

        Calendar calendar = Calendar.getInstance();
        Log.d("TOday", "check_today: " +Calendar.getInstance().DAY_OF_WEEK);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return alarmDB.isSun();
            case Calendar.MONDAY:
                return alarmDB.isMon();
            case Calendar.TUESDAY:
                return alarmDB.isTues();
                case Calendar.WEDNESDAY:
                    return alarmDB.isWed();

            case Calendar.THURSDAY:
                return alarmDB.isThurs();

            case Calendar.FRIDAY:
                return alarmDB.isFri();
            case Calendar.SATURDAY:
                return alarmDB.isSat();
            default:
                return false;

        }
    }
}