package com.example.alarmapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AlarmService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();


//        Intent intent=Intent.getIntent();
//        startForeground(1,new Notification());



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {




        Log.d("Alarm Servce", "onStartCommand: Service has started ");
        int id=intent.getExtras().getInt("alarm_id");
        Log.d("Start", "onStartCommand: "+id);
        String channel_id="AlarmApp";
        CharSequence sequence="Sequence";
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent notification_intent=new Intent(getApplicationContext(),NotificationActi.class);
        notification_intent.putExtra("alarm_id",id);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,notification_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel=new NotificationChannel(channel_id,sequence, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
            Notification.Builder builder=new Notification.Builder(getApplicationContext()).setContentTitle("Notification Recieved").setChannelId(channel_id)
                    .setContentText("Alarm Trigerred").setAutoCancel(true).setContentIntent(pendingIntent).setSmallIcon(R.drawable.calendar);
            startForeground(1,builder.build());

        }


        return START_NOT_STICKY;

    }



    @Override
    public void onDestroy() {
        super.onDestroy();



    }
}
