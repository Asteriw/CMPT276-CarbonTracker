package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setupJourneyButtons();
        setupFootprintButtons();
    }

    private void setupFootprintButtons() {
        // directs to "CarboonFootPrint" screen
        Button footprint_button = (Button) findViewById(R.id.view_cabon_footprint_button);
        footprint_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainMenu2CarbonFootPrint = CarbonFootPrint.makeIntent(MainMenu.this);
                startActivity(MainMenu2CarbonFootPrint);
            }
        });
    }

    private void setupJourneyButtons() {
        // directs to "SelectJourney" screen
        Button journey_button = (Button) findViewById(R.id.create_a_new_journey_button);
        journey_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainMenu2SelectJourney = SelectJourney.makeIntent(MainMenu.this);
                startActivity(MainMenu2SelectJourney);
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainMenu.class);
    }
}
