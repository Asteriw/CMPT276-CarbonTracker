package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
*  Add Car saves nickname,make,model and year of a car user selects from CSV
* */
public class AddCar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        setupBackButton();
        setupOKButton();
    }

    private void setupOKButton() {
        Button ok_button = (Button) findViewById(R.id.ok_button_add_car);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save a car and send it to the carlist

                finish();
            }
        });
    }

    private void setupBackButton() {
        Button back_button = (Button) findViewById(R.id.cancel_button_add_car);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCar.class);
    }
}