package com.cmpt276.kenneyw.carbonfootprinttracker;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DataActivityPicker extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_data);
        setupButtons();
    }

    private void setupButtons() {
        Button cancel = (Button) findViewById(R.id.choose_cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button journeys = (Button) findViewById(R.id.choose_journeys_data);
        journeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FootPrintMenu2Journey = CarbonFootPrint.makeIntent(DataActivityPicker.this);
                startActivity(FootPrintMenu2Journey);
            }
        });
        Button utilities = (Button) findViewById(R.id.choose_utilities_data);
        utilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FootPrintMenu2Utility = UtilitiesFootPrint.makeIntent(DataActivityPicker.this);
                startActivity(FootPrintMenu2Utility);
            }
        });
    }
    public static Intent makeIntent(Context context) {
        return new Intent(context, DataActivityPicker.class);
    }

}
