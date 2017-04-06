package com.cmpt276.kenneyw.carbonfootprinttracker.ui;

/**
 * A welcome screen with an animation. Cannot return to this screen without relaunching the app.
 * Leads to main menu.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.cmpt276.kenneyw.carbonfootprinttracker.R;

public class WelcomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        setupButton();
        setupAnimation();
        showDefaultNotification();
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

    public void showDefaultNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainMenu.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.car_notification)
                .setContentTitle(r.getString(R.string.notification_title))
                .setContentText(r.getString(R.string.notification_text))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    public void showBillsNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, SelectUtilities.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.car_notification)
                .setContentTitle(r.getString(R.string.notification_title))
                .setContentText(String.format(getString(R.string.notification_text_bills), 1)) //TODO: Change this value
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    public void showJourneysNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, SelectJourney.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.car_notification)
                .setContentTitle(r.getString(R.string.notification_title))
                .setContentText(String.format(getString(R.string.notification_text_journeys_more_than_one), 1))//// TODO: CHANGE THIS VALUE and journeys >1
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, WelcomeScreen.class);
    }
}