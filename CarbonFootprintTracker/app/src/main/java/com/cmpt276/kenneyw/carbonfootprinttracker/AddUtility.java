package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddUtility extends AppCompatActivity {
    public static final int START_DATE_CHOOSE = 3;
    public static final int END_DATE_CHOOSE = 4;
    String utility_type;
    EditText editAmount;
    EditText editNumPeople;
    EditText editStartDate;
    EditText editEndDate;
    EditText editNickname;
    RadioButton electricityButton;
    RadioButton naturalgasButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    private void setupButton() {
        Button cancel_button = (Button) findViewById(R.id.utility_add_cancel);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(RESULT_CANCELED, i);
                finish();
            }
        });

        Button ok_button = (Button) findViewById(R.id.ok_button_add_utility);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromUser();
                setResult(RESULT_OK);
                finish();



                Intent i = new Intent();
                setResult(RESULT_OK, i);
            }
        });

        Button startDate=(Button)findViewById(R.id.startdate_btn);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddUtility2EditDate = EditDate.makeIntent(AddUtility.this);
                startActivityForResult(AddUtility2EditDate,START_DATE_CHOOSE);
            }
        });
        Button endDate=(Button)findViewById(R.id.enddate_btn);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AddUtility2EditDate = EditDate.makeIntent(AddUtility.this);
                startActivityForResult(AddUtility2EditDate,END_DATE_CHOOSE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case START_DATE_CHOOSE:
                DateSingleton finalDate = DateSingleton.getInstance();
                
                break;
            case END_DATE_CHOOSE:
                break;
        }
    }

    //fix
    private void getDataFromUser() {
        // Save a utility
        editAmount = (EditText) findViewById(R.id.amount_text);
        editNumPeople = (EditText) findViewById(R.id.num_people_text);
        editNickname = (EditText) findViewById(R.id.utility_nick_name_text);
        electricityButton = (RadioButton) findViewById(R.id.radioElectricity);
        naturalgasButton = (RadioButton) findViewById(R.id.radioNaturalGas);

        // no inputs entered
        if (editAmount.getText().toString().equals("") || editNumPeople.getText().toString().equals("") && editNickname.getText().toString().equals("")
                && !electricityButton.isChecked() && !naturalgasButton.isChecked()) {
            Toast.makeText(getApplicationContext(), "No inputs entered", Toast.LENGTH_SHORT).show();
        }
        // something was entered
        else {

            if (editAmount.getText().charAt(0) == ' ') {
                Toast.makeText(getApplicationContext(), "Amount can't be left empty", Toast.LENGTH_SHORT).show();
            } else if (utility_type.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please select a utility type", Toast.LENGTH_SHORT).show();
            } else if (editNickname.getText().charAt(0) == ' ') {
                Toast.makeText(getApplicationContext(), "Name can't be left empty", Toast.LENGTH_SHORT).show();
            } else if (editNumPeople.getText().charAt(0) == ' ') {
                Toast.makeText(getApplicationContext(), "Enter a number of poeple", Toast.LENGTH_SHORT).show();
            } else {

                //String name, String gasType, double amounts, int num_people, double emission, String startDate, String endDate
                UtilitySingleton new_utility = UtilitySingleton.getInstance();
                new_utility.setAmounts(Double.parseDouble(editAmount.getText().toString()));
                new_utility.setNum_poeople(Integer.parseInt(editNumPeople.getText().toString()));
                new_utility.setStartDate(editStartDate.getText().toString());
                new_utility.setEndDate(editEndDate.getText().toString());
                new_utility.setName(editNickname.getText().toString());
                new_utility.setEmission(0.0);
                new_utility.setGasType(utility_type);
                Log.i("AddUtility", "Singleton = " + new_utility.getName());
                Log.i("AddUtility", "Singleton = " + new_utility.getGasType());
            }
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddUtility.class);
    }
}
