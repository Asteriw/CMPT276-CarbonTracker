package com.cmpt276.kenneyw.carbonfootprinttracker.ui;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cmpt276.kenneyw.carbonfootprinttracker.R;

/*
*   This class creates a spalsh screen with a progress bar
* Code for spalsh screen wwas retrieved from :http://abhiandroid.com/ui/progressbar
 */

public class SplashScreen extends AppCompatActivity {
    ProgressBar loadingBar;
    TextView loadingText;
    Thread splashTread; // Called when the activity is first created.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        setupSplashScreen();
    }

    private void setupSplashScreen() {
        loadingBar = (ProgressBar) findViewById(R.id.progressBar);
        loadingText = (TextView) findViewById(R.id.SplashLoading);
        loadingBar.setProgress(0); // set progress value to 0
        loadingBar.setMax(3000);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0; // get progress value from the progress bar
                    // Splash screen pause time
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                        loadingBar.setProgress(waited);
                    }
                    Intent intent = new Intent(SplashScreen.this, MainMenu.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                }
            }
        };
        splashTread.start();
    }
}