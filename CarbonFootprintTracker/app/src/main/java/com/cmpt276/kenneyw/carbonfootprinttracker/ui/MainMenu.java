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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cmpt276.kenneyw.carbonfootprinttracker.R;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setupButtons();
        setupAnimation();
    }

    private void setupAnimation() {
        ImageView paper_plane = (ImageView) findViewById(R.id.mainmeu_plane);
        Animation flyingAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flying);
        paper_plane.startAnimation(flyingAnimation);
    }

    private void setupButtons() {
        // directs to "SelectJourney" screen
        ImageButton journey_button = (ImageButton) findViewById(R.id.create_a_new_journey_button);
        journey_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainMenu2SelectJourney = SelectJourney.makeIntent(MainMenu.this);
                startActivity(MainMenu2SelectJourney);
            }
        });
        // directs to "CarbonFootPrint" screen
        ImageButton footprint_button = (ImageButton) findViewById(R.id.view_cabon_footprint_button);
        footprint_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainMenu2DataActivityPicker = DataActivityPicker.makeIntent(MainMenu.this);
                startActivity(MainMenu2DataActivityPicker);
            }
        });
        // directs to "Utility Bill" screen
        ImageButton utility_button = (ImageButton) findViewById(R.id.utility_add_button);
        utility_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainMenu2Utilities = SelectUtilities.makeIntent(MainMenu.this);
                startActivity(MainMenu2Utilities);
            }
        });
        // directs to "About" screen
        ImageButton about_button = (ImageButton) findViewById(R.id.about_btn);
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
