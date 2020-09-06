package com.example.alarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlarmDB extends RealmObject {
    @PrimaryKey
    int alarm_id;
    private String name;
    private int year, month, date, hour, minute;
    private boolean repeat, sun, mon, tues, wed, thurs, fri, sat,status;

    public AlarmDB() {
    }

    public AlarmDB(int alarm_id, String name, int year, int month, int date, int hour, int minute, boolean repeat, boolean sun, boolean mon, boolean tues, boolean wed, boolean thurs, boolean fri, boolean sat, boolean status) {
        this.alarm_id = alarm_id;
        this.name = name;
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.repeat = repeat;
        this.sun = sun;
        this.mon = mon;
        this.tues = tues;
        this.wed = wed;
        this.thurs = thurs;
        this.fri = fri;
        this.sat = sat;
        this.status = status;
    }

    public int getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(int alarm_id) {
        this.alarm_id = alarm_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isSun() {
        return sun;
    }

    public void setSun(boolean sun) {
        this.sun = sun;
    }

    public boolean isMon() {
        return mon;
    }

    public void setMon(boolean mon) {
        this.mon = mon;
    }

    public boolean isTues() {
        return tues;
    }

    public void setTues(boolean tues) {
        this.tues = tues;
    }

    public boolean isWed() {
        return wed;
    }

    public void setWed(boolean wed) {
        this.wed = wed;
    }

    public boolean isThurs() {
        return thurs;
    }

    public void setThurs(boolean thurs) {
        this.thurs = thurs;
    }

    public boolean isFri() {
        return fri;
    }

    public void setFri(boolean fri) {
        this.fri = fri;
    }

    public boolean isSat() {
        return sat;
    }

    public void setSat(boolean sat) {
        this.sat = sat;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}



