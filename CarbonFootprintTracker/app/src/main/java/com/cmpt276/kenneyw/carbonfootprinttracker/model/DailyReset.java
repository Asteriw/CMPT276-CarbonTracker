package com.cmpt276.kenneyw.carbonfootprinttracker.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class DailyReset extends BroadcastReceiver {
    private static final String ADDEDTODAY ="addedToday";

    @Override
    public void onReceive(Context context, Intent intent){
        resetDailyJourneys(context);
        Log.i("TAG", "TESTING");
    }

    public void resetDailyJourneys(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putInt(ADDEDTODAY, 0);
        editor.apply();
    }
}
