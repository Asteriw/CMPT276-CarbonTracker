package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddUtility extends AppCompatActivity{
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
        utility_radioButton.clearCheck();
        utility_radioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext(), selectedButton.getText() + " selected", Toast.LENGTH_SHORT).show();
                utility_type = selectedButton.getText().toString();
            }
        });
    }
    private void getDataFromUser(){
        // Save a utility
        EditText editAmount = (EditText) findViewById(R.id.amount_text);
        EditText editNumPeople = (EditText) findViewById(R.id.num_people_text);
        EditText editStartDate = (EditText) findViewById(R.id.start_date_text);
        EditText editEndDate = (EditText) findViewById(R.id.end_date_text);
        EditText editNickname = (EditText) findViewById(R.id.utility_nick_name_text);

        //String name, String gasType, double amounts, int num_people, double emission, String startDate, String endDate
        UtilitySingleton new_utility = UtilitySingleton.getInstance();
        new_utility.setAmounts( Double.parseDouble( editAmount.getText().toString() ));
        new_utility.setNum_poeople( Integer.parseInt( editNumPeople.getText().toString() ));
        new_utility.setStartDate(  editStartDate.getText().toString() );
        new_utility.setEndDate(  editEndDate.getText().toString() );
        new_utility.setName( editNickname.getText().toString() );
        new_utility.setEmission( 0.0 );
        new_utility.setGasType( utility_type );
        setResult(RESULT_OK);
        Log.i("AddUtility", "Singleton = "+ new_utility.getName());
        Log.i("AddUtility", "Singleton = "+ new_utility.getGasType());
        finish();
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
                getDataFromUser();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddUtility.class);
    }
}
