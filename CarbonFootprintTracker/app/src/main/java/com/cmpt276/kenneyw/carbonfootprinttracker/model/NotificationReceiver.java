package com.cmpt276.kenneyw.carbonfootprinttracker.model;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.cmpt276.kenneyw.carbonfootprinttracker.R;
import com.cmpt276.kenneyw.carbonfootprinttracker.ui.MainMenu;
import com.cmpt276.kenneyw.carbonfootprinttracker.ui.SelectJourney;
import com.cmpt276.kenneyw.carbonfootprinttracker.ui.SelectUtilities;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;



public class NotificationReceiver extends BroadcastReceiver {
    private static final String SHAREDPREF_SET = "CarbonFootprintTrackerJournies";
    private static final String SHAREDPREF_ITEM_AMOUNTOFJOURNEYS = "AmountOfJourneys";
    private static final String SHAREDPREF_SET_2 = "CarbonFootprintTrackerUtilities";
    private static final String SHAREDPREF_ITEM_AMOUNTOFUTILITIES = "AmountOfUtilities";

    int journeysAmount;
    int journeysTodayAmount;
    int utilitiesMonthAmount;
    int utilitiesAmount;
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    @Override
    public void onReceive(Context context, Intent intent){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        journeysAmount = preferences.getInt(SHAREDPREF_ITEM_AMOUNTOFJOURNEYS, 0);
        journeysTodayAmount = preferences.getInt(SHAREDPREF_ITEM_AMOUNTOFJOURNEYS, 0); //TODO
        utilitiesAmount = preferences.getInt(SHAREDPREF_ITEM_AMOUNTOFUTILITIES, 0);
        utilitiesMonthAmount = preferences.getInt(SHAREDPREF_ITEM_AMOUNTOFUTILITIES, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (journeysAmount == 0 && utilitiesAmount == 0){
           showDefaultNotification(context);
        } else if (utilitiesMonthAmount == 0){
            showBillsNotification(context);
        } else if (journeysTodayAmount == 0){
            showJourneysNotification(context);
        } else {
            showMoreNotification(context);
        }
        Log.i("TAG", "TESTING");
    }

    public void showDefaultNotification(Context context) {
        Intent intent = new Intent(context, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        Resources r = context.getResources();
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.car_notification)
                .setContentTitle(r.getString(R.string.notification_title))
                .setContentText(r.getString(R.string.notification_text))
                .setContentIntent(pi)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    public void showBillsNotification(Context context) {
        Intent intent = new Intent(context, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        Resources r = context.getResources();
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.car_notification)
                .setContentTitle(r.getString(R.string.notification_title))
                .setContentText(String.format(context.getString(R.string.notification_text_bills), 1)) //TODO: Change this value
                .setContentIntent(pi)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    public void showJourneysNotification(Context context) {
        Intent intent = new Intent(context, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        Resources r = context.getResources();
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.car_notification)
                .setContentTitle(r.getString(R.string.notification_title))
                .setContentText(String.format(context.getString(R.string.notification_text_journeys_more_than_one), 1))//// TODO: CHANGE THIS VALUE and journeys >1
                .setContentIntent(pi)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    public void showMoreNotification(Context context) {
        Intent intent = new Intent(context, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        Resources r = context.getResources();
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.car_notification)
                .setContentTitle(r.getString(R.string.notification_title))
                .setContentText(String.format(context.getString(R.string.notification_text_journeys_more_than_one), 1))//// TODO: CHANGE THIS VALUE and journeys >1
                .setContentIntent(pi)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

}
