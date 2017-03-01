package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setupStartButton();
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
