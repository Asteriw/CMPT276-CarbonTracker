package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    String[] yearsArray = {"1984","1985","1986","1987","1988","1989",
                           "1990","1991","1992","1993","1994","1995",
                           "1996","1997","1998","1999","2000","2001",
                           "2002","2003","2004","2005","2006","2007",
                           "2008","2009","2010","2011","2012","2013",
                           "2014","2015","2016","2017","2018","2019"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        openDatabase();
        setupYearSpinner();
        setupMakeSpinner("2004");
        setupModelSpinner("2004", "Honda");
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
        ArrayAdapter<CharSequence> yearAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearsArray);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
    }

    private void setupMakeSpinner(String year) {
        Spinner yearSpinner = (Spinner) findViewById(R.id.make_spinner);
        List<String> makeList = DatabaseAccess.getInstance(this).getMakes(year);
        ArrayAdapter<CharSequence> yearAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, makeList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
    }

    private void setupModelSpinner(String year, String make) {
        Spinner yearSpinner = (Spinner) findViewById(R.id.model_spinner);
        List<String> modelList = DatabaseAccess.getInstance(this).getModels(year, make);
        ArrayAdapter<CharSequence> yearAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, modelList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
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