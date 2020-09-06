package com.example.alarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.realm.Realm;

public class NotificationActi extends AppCompatActivity {
   Button button;
   int id;
   TextView textView;
   Glide glide;
   ImageView imageView;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notification);
        imageView=findViewById(R.id.img_gif);
        textView=findViewById(R.id.name);
        button=findViewById(R.id.dismiss);
        Intent intent1=getIntent();
        id=intent1.getExtras().getInt("alarm_id");
        Glide.with(this).load(R.drawable.alarm).into(imageView);

        Realm realm=Realm.getDefaultInstance();
        AlarmDB alarmDB=realm.where(AlarmDB.class).equalTo("alarm_id",id).findFirst();
        textView.setText(alarmDB.getName());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent reciever=new Intent(NotificationActi.this,AlarmReciever.class);
//                PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),id,reciever,PendingIntent.FLAG_UPDATE_CURRENT);
//                AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
//                alarmManager.cancel(pendingIntent);

                AlarmReciever.ringtone.stop();
                stopService(new Intent(getApplicationContext(),AlarmService.class));
                finish();

            }
        });
    }
}