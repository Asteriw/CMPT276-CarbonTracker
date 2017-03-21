package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddUtility extends AppCompatActivity{
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
                RadioButton selectedButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext(), selectedButton.getText() + " selected", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupButton() {
        Button button = (Button) findViewById(R.id.utility_add_cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, AddUtility.class);
    }
}
