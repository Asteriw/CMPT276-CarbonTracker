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
        
        setupButtons();
    }

    private void setupButtons() {
        Button startCreate = (Button) findViewById(R.id.btncreatenew);
        startCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newJourneyIntent = SelectTransportation.makeIntent(MainMenu.this);
                startActivity(newJourneyIntent);
            }
        });
        Button startSettings = (Button) findViewById(R.id.btnviewtracked);
        startSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ViewTrackedIntent = ViewTracked.makeIntent(MainMenu.this);
                startActivity(ViewTrackedIntent);
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainMenu.class);
    }
}
