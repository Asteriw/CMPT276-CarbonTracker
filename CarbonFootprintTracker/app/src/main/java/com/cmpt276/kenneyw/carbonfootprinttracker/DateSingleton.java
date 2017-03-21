package com.cmpt276.kenneyw.carbonfootprinttracker;
/*
Date Singleton for storing date in journey
 */

import android.icu.text.SimpleDateFormat;

import java.util.Date;

public class DateSingleton {
    private static DateSingleton dateinstance = null;

    private DateSingleton(){
        this.dateString="";
    }

    public static DateSingleton getInstance(){
        if(dateinstance == null){
            dateinstance = new DateSingleton();
        }
        return dateinstance;
    }

    private String dateString;

    public void setDateString(String date) {
        this.dateString = date;
    }
}
