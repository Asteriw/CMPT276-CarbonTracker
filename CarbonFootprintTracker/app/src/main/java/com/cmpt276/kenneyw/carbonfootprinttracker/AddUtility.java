package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddUtility extends AppCompatActivity{
    RadioButton selectedButton;
    String utility_type;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_utility);
        setupButton();
        setupRadioButtons();
    }

    private void setupRadioButtons() {
        RadioGroup utility_radioButton = (RadioGroup) findViewById(R.id.radioUtility);
        utility_radioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Toast.makeText(getApplicationContext(), "" + checkedId + " selected", Toast.LENGTH_SHORT).show();
                selectedButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext(), selectedButton.getText() + " selected", Toast.LENGTH_SHORT).show();
                utility_type = selectedButton.getText().toString();
            }
        });
    }


    private void setupButton() {
        Button cancel_button = (Button) findViewById(R.id.utility_add_cancel);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button ok_button = (Button) findViewById(R.id.ok_button_add_utility);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editAmount = (EditText) findViewById(R.id.amount_text);
                EditText editNumPeople = (EditText) findViewById(R.id.num_people_text);
                EditText editStartDate = (EditText) findViewById(R.id.start_date_text);
                EditText editEndDate = (EditText) findViewById(R.id.end_date_text);
                EditText editNickname = (EditText) findViewById(R.id.utility_nick_name_text);

                // Save a utility
                //String name, String gasType, double amounts, int num_people, double emission, String startDate, String endDate

            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddUtility.class);
    }
}
