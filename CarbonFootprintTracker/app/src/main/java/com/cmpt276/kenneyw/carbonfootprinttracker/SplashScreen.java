package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setupStartButton();
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

    private void setupStartButton() {
        Button startMenu = (Button) findViewById(R.id.btnsplashstart);
        startMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = MainMenu.makeIntent(SplashScreen.this);
                startActivity(mainMenuIntent);
                SplashScreen.this.finish();
            }
        });
    }


}
