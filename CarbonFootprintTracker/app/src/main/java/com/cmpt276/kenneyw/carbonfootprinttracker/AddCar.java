package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


/*
*  Add Car saves nickname,make,model and year of a car user selects from CSV
* */
public class AddCar extends AppCompatActivity {

    DatabaseAccess databaseAccess;
    ArrayList<String> makeList;
    ArrayList<String> yearList;
    String selectedYear;
    String selectedMake;
    String selectedModel;
    int yearSelected = 0;
    int makeSelected = 0;
    int modelSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        openDatabase();
        setupYearSpinner();
        setupBackButton();
        setupOKButton();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        closeDatabase();
    }

    private void setupYearSpinner() {
        Spinner yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        List<String> yearList = DatabaseAccess.getInstance(this).getYears();
        ArrayAdapter<CharSequence> yearAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setOnItemSelectedListener(new yearSpinner());
        yearSpinner.setAdapter(yearAdapter);
    }
    private class yearSpinner implements AdapterView.OnItemSelectedListener {
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            if (position == 0 && yearSelected == 0){
                yearSelected = 1;
            } else {
                selectedYear = parent.getItemAtPosition(position).toString();
                Log.i("This", selectedYear);
                setupMakeSpinner(selectedYear);
            }
        }
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }

    private void setupMakeSpinner(String year) {
        Spinner makeSpinner = (Spinner) findViewById(R.id.make_spinner);
        List<String> makeList = DatabaseAccess.getInstance(this).getMakes(year);
        ArrayAdapter<CharSequence> makeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, makeList);
        makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeSpinner.setOnItemSelectedListener(new makeSpinner());
        makeSpinner.setAdapter(makeAdapter);
    }

    private class makeSpinner implements AdapterView.OnItemSelectedListener {
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            if (position == 0 && makeSelected == 0){
                makeSelected = 1;
            } else {
                selectedMake = parent.getItemAtPosition(position).toString();
                Log.i("This", selectedMake);
                setupModelSpinner(selectedYear, selectedMake);
            }
        }
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }

    private void setupModelSpinner(String year, String make) {
        Spinner modelSpinner = (Spinner) findViewById(R.id.model_spinner);
        List<String> modelList = DatabaseAccess.getInstance(this).getModels(year, make);
        ArrayAdapter<CharSequence> modelAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, modelList);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSpinner.setOnItemSelectedListener(new modelSpinner());
        modelSpinner.setAdapter(modelAdapter);
    }

    private class modelSpinner implements AdapterView.OnItemSelectedListener {
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            if (position == 0 && modelSelected == 0){
                modelSelected = 1;
            } else {
                selectedModel = parent.getItemAtPosition(position).toString();
                Log.i("This", selectedModel);
                makeCar(selectedYear, selectedMake, selectedModel);
            }
        }
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }

    private void makeCar(String yearSelected, String modelSelected, String makeSelected) {
        
    }

    private void closeDatabase() {
        databaseAccess.close();
    }

    private void openDatabase() {
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
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