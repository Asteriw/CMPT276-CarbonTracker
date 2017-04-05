package com.cmpt276.kenneyw.carbonfootprinttracker.ui;

/**
 * Main menu. Is connected to the SelectJourney, CarbonFootprint and Utilities screens.
 * Nothing too special.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmpt276.kenneyw.carbonfootprinttracker.R;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setupButtons();
        setupAnimation();
    }
    // Fade carlogo in the MainMenu Screen
    private void setupAnimation(){
        ImageView myImageView = (ImageView) findViewById(R.id.car_logo);
        Animation carAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        myImageView.startAnimation(carAnimation);
    }
    private void setupButtons() {
        // directs to "SelectJourney" screen
        Button journey_button = (Button) findViewById(R.id.create_a_new_journey_button);
        journey_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainMenu2SelectJourney = SelectJourney.makeIntent(MainMenu.this);
                startActivity(MainMenu2SelectJourney);
            }
        });
        // directs to "CarbonFootPrint" screen
        Button footprint_button = (Button) findViewById(R.id.view_cabon_footprint_button);
        footprint_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainMenu2DataActivityPicker = DataActivityPicker.makeIntent(MainMenu.this);
                startActivity(MainMenu2DataActivityPicker);
            }
        });
        // directs to "Utility Bill" screen
        Button utility_button = (Button) findViewById(R.id.utility_add_button);
        utility_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainMenu2Utilities = SelectUtilities.makeIntent(MainMenu.this);
                startActivity(MainMenu2Utilities);
            }
        });
        // directs to "About" screen
        Button about_button = (Button) findViewById(R.id.about_btn);
        about_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                AboutFragment dialog = new AboutFragment();
                dialog.show(manager, "About");
            }
        });
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, MainMenu.class);
    }
}
