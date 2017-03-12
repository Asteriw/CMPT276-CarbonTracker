package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        setupButton();
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
