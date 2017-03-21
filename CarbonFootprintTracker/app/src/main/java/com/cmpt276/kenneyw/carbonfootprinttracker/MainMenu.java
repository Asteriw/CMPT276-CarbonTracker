package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setupJourneyButtons();
        setupFootprintButtons();
        setupTipButton();
    }

    private void setupTipButton() {
        Button TipButton = (Button) findViewById(R.id.tiptextbutton);
        TipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipMaker();
            }
        });
    }

    int tipCounter = 0;
    String tipString = "";

    private void tipMaker() {
        LayoutInflater inflater = LayoutInflater.from(MainMenu.this);
        View tipView =inflater.inflate(R.layout.activity_tip_dialog, null);

        TextView tipText = (TextView) tipView.findViewById(R.id.tip_text);
        tipText.setGravity(Gravity.CENTER);
        tipText.setText(tipTextSelector());
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
        builder.setTitle("Tips!");
        //alertDialog.setMessage("Here is a really long message.");
        builder.setView(tipView);
        builder.setPositiveButton("Next Tip", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tipMaker();
                Log.i("Dialog", "We got here once");
            }
        });
        builder.setNegativeButton("Close Tips", null);
        AlertDialog tipDialog = builder.create();
        tipDialog.show();
    }

    private CharSequence tipTextSelector() {
        if (tipCounter==0) {
            tipString = "Tipperoni";
            tipCounter++;
            return tipString;
        }
        else if (tipCounter==1) {
            tipString = "Just the tips";
            tipCounter++;
        }
        else if (tipCounter>1) {
            tipString = "No more Tips for you!";
            tipCounter++;
        }
        return tipString;
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
