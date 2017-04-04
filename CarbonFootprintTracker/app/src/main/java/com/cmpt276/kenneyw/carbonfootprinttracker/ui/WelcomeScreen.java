package com.cmpt276.kenneyw.carbonfootprinttracker.ui;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    public static Intent makeIntent(Context context) {
        return new Intent(context, WelcomeScreen.class);
    }
}