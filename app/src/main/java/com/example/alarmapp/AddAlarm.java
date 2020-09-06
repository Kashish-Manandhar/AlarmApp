package com.example.alarmapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmModel;

public class AddAlarm extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    CheckBox repeat;
    ToggleButton sun,mon,tues,wed,thurs,fri,sat;;
    EditText alarm_name;
    Button save;
    LinearLayout days;
    boolean is_sun,is_mon,is_tues,is_wed,is_thurs,is_fri,is_sat,is_repeat;
    int hour,minutes;
    TimePicker timePicker;
    ImageView cal;
    private static int Cal_Code=1;
    Calendar calendar;
    int year,month,date;
    AlarmManager alarmManager;
    Realm realm;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
      getSupportActionBar().setTitle("Add Alarm");
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
       days=findViewById(R.id.days);
       save=findViewById(R.id.save);
       alarm_name=findViewById(R.id.alarmnameET);
       repeat=findViewById(R.id.repeat);
       sun=findViewById(R.id.sun);
       mon=findViewById(R.id.mon);
       tues=findViewById(R.id.tues);
       wed=findViewById(R.id.wed);
       thurs=findViewById(R.id.thurs);
       fri=findViewById(R.id.fri);
       cal=findViewById(R.id.img_cal);



       sat=findViewById(R.id.sat);

       repeat.setOnCheckedChangeListener(this);
       days.setVisibility(View.GONE);
       sun.setOnCheckedChangeListener(this);
       mon.setOnCheckedChangeListener(this);
       tues.setOnCheckedChangeListener(this);
       wed.setOnCheckedChangeListener(this);
       thurs.setOnCheckedChangeListener(this);
       fri.setOnCheckedChangeListener(this);
       sat.setOnCheckedChangeListener(this);
       timePicker=findViewById(R.id.clock);
       calendar=Calendar.getInstance();


       year=calendar.get(Calendar.YEAR);
       month=calendar.get(Calendar.MONTH);
       date=calendar.get(Calendar.DAY_OF_MONTH);



       cal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivityForResult(new Intent(AddAlarm.this,CalendarActivity.class),Cal_Code);
           }
       });

       save.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               saveAlarm();
           }
       });


    }


    private void saveAlarm() {

            realm=Realm.getDefaultInstance();
            final String name=alarm_name.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hour=timePicker.getHour();
                minutes=timePicker.getMinute();
            }
            else
            {
                hour=timePicker.getCurrentHour();
                minutes=timePicker.getCurrentMinute();
            }

            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,date);
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minutes);
            calendar.set(Calendar.SECOND,0);

           realm.executeTransaction(new Realm.Transaction() {
               @Override
               public void execute(Realm realm) {
                   Number alarm_id = realm.where(AlarmDB.class).max("alarm_id");
                   if(alarm_id==null)
                   {
                       id=1;
                   }
                   else
                   {
                       id=alarm_id.intValue()+1;
                   }
                   AlarmDB alarmDB=realm.createObject(AlarmDB.class,id);
                   alarmDB.setName(name);
                   alarmDB.setYear(year);
                   alarmDB.setMonth(month);
                   alarmDB.setDate(date);
                   alarmDB.setHour(hour);
                   alarmDB.setMinute(minutes);
                   alarmDB.setRepeat(is_repeat);
                   alarmDB.setSun(is_sun);
                   alarmDB.setMon(is_mon);
                   alarmDB.setTues(is_tues);
                   alarmDB.setWed(is_wed);
                   alarmDB.setThurs(is_thurs);
                   alarmDB.setFri(is_fri);
                   alarmDB.setSat(is_sat);
                   alarmDB.setStatus(true);

               }
           });
        Intent reciever_intent=new Intent(this,AlarmReciever.class);
        reciever_intent.putExtra("alarm_id",id);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),id,reciever_intent,0);

        if(is_repeat)
        {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        else
        {
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }










        Log.d("On Clicked", "saveAlarm: "+name+hour+":"+minutes+is_repeat+is_fri+"\n"+year+"/"+month+"/"+date);
        Intent activity=new Intent(AddAlarm.this,MainActivity.class);
        activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(activity);
        finish();

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId())
        {
            case R.id.repeat:
                if(isChecked) {
                    is_repeat=true;
                    days.setVisibility(View.VISIBLE);
                }
                else
                {
                    is_repeat=false;
                    sun.setChecked(false);
                    mon.setChecked(false);
                    tues.setChecked(false);
                    wed.setChecked(false);
                    thurs.setChecked(false);
                    sat.setChecked(false);
                    fri.setChecked(false);
                    days.setVisibility(View.GONE);
                }
                break;

            case R.id.sun:
                is_sun= isChecked;
                break;
            case R.id.mon:
                is_mon= isChecked;
                break;
            case R.id.tues:
                is_tues= isChecked;
                break;
            case R.id.wed:
                is_wed= isChecked;
                break;
            case R.id.thurs:
                is_thurs= isChecked;
                break;
            case R.id.fri:
                is_fri= isChecked;
                break;
            case R.id.sat:
                is_sat= isChecked;
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Cal_Code&&resultCode==RESULT_OK)
        {
             year=data.getExtras().getInt("Year");
             month=data.getExtras().getInt("Month");
             date=data.getExtras().getInt("Day");
        }

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        MyApplication.activitytResumed();
//    }
//
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MyApplication.activityPaused();
//    }
}