package com.cmpt276.kenneyw.carbonfootprinttracker.ui;

/**
 * A welcome screen with an animation. Cannot return to this screen without relaunching the app.
 * Leads to main menu.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.cmpt276.kenneyw.carbonfootprinttracker.R;
import com.cmpt276.kenneyw.carbonfootprinttracker.model.DailyReset;
import com.cmpt276.kenneyw.carbonfootprinttracker.model.NotificationReceiver;

public class WelcomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        setupButton();
        setupAnimation();
        notificationSpawner();
        dailyChanger();
        monthlyChanger();
    }

    private void monthlyChanger() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void dailyChanger() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Intent intent = new Intent(getApplicationContext(), DailyReset.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void setupAnimation() {
        ImageView car = (ImageView) findViewById(R.id.splashcar);
        TranslateAnimation slide = new TranslateAnimation(-400f, 1400f, -20f, -20f);
        slide.setInterpolator(new LinearInterpolator());
        slide.setDuration(3500);
        slide.setRepeatCount(Animation.INFINITE);
        car.startAnimation(slide);
    }
    private void setupButton() {
        // Directs to "MainMenu" screen and kills "WelcomeScreen"
        Button start_button = (Button) findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent WelcomeScreen2MainMenu = MainMenu.makeIntent(WelcomeScreen.this);
                startActivity(WelcomeScreen2MainMenu);
                finish();
            }
        });
    }

    private void notificationSpawner(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, WelcomeScreen.class);
    }
}