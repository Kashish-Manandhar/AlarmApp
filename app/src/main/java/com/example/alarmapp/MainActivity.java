package com.example.alarmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements AlarmAdapter.onClickListener {
    TextView textView;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    AlarmAdapter alarmAdapter;
    List<AlarmDB> list;
    Realm realm;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.message_noItem);
        recyclerView=findViewById(R.id.recyclerView);
        textView.setVisibility(View.GONE);
        realm=Realm.getDefaultInstance();
        list=new ArrayList<>();
        list=realm.where(AlarmDB.class).findAll().sort("alarm_id");
                if(list.size()==0)
        {
            textView.setVisibility(View.VISIBLE);
        }

        Log.d("List size", "onCreate: "+list.size());
                alarmAdapter=new AlarmAdapter(this,list);
                alarmAdapter.notifyDataSetChanged();
                manager=new LinearLayoutManager(this);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(alarmAdapter);

        fab=findViewById(R.id.add_alarm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddAlarm.class));

            }
        });





    }

    @Override
    public void setonCLick(int position, View view) {
        showPopup(position,view);
    }

    @Override
    public void setChecked(int position, boolean isChecked) {
         AlarmDB alarmDB=list.get(position);
         if(!isChecked)
         {
             realm.beginTransaction();
             alarmDB.setStatus(false);
             realm.commitTransaction();

         }
         else
         {
             realm.beginTransaction();
             alarmDB.setStatus(true);
             realm.commitTransaction();

         }
    }

    private void showPopup(final int positon, View view)
    {
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.popupmenu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:

                       AlarmDB curr_alarm=list.get(positon);
                       realm.beginTransaction();
                       realm.where(AlarmDB.class).equalTo("alarm_id",curr_alarm.getAlarm_id()).findFirst().deleteFromRealm();
                       realm.commitTransaction();
                       alarmAdapter.notifyDataSetChanged();


                       if(list.size()==0)
                       {
                           textView.setVisibility(View.VISIBLE);
                       }
                       return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();


    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        MyApplication.activityPaused();
//
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        MyApplication.activitytResumed();
//    }
}