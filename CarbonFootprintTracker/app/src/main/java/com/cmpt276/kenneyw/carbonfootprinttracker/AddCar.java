package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

public class AddCar extends AppCompatActivity{

    Car car;
    private ArrayList<Integer> yearList = new ArrayList<>();
    private ArrayList<String> makeList = new ArrayList<>();
    private ArrayList<String> modelList = new ArrayList<>();
    int yearSelected;
    int makeSelected;
    int modelSelected;
    ArrayAdapter<String> makeAdapter;
    ArrayAdapter<String> modelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        getYearData();
        setupSpinners();

        //setupRouteButton();
    }

    private void getYearData() {
        InputStream stream = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            //Step over headers
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                //Split by ','
                String[] tokens = line.split(",");
                if(!yearList.contains(Integer.parseInt(tokens[20]))){
                    yearList.add(Integer.parseInt(tokens[20]));
                }
            }
            Collections.sort(yearList);
        } catch (IOException e){
            Log.wtf("CarbonFootprintReader", "Error reading vehicles database on line " + line, e);
            e.printStackTrace();
        }
    }

    private void getMakeData(int year) {
        InputStream stream = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            //Step over headers
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                //Split by ','
                String[] tokens = line.split(",");
                if(Integer.parseInt(tokens[20]) == year){
                    if(!makeList.contains(tokens[1])){
                        makeList.add(tokens[1]);
                    }
                }
            }
            Collections.sort(makeList);
        } catch (IOException e){
            Log.wtf("CarbonFootprintReader", "Error reading vehicles database on line " + line, e);
            e.printStackTrace();
        }
    }

    private void getModelData(int year, String make) {
        InputStream stream = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            //Step over headers
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                //Split by ','
                String[] tokens = line.split(",");
                if(Integer.parseInt(tokens[20]) == year && tokens[1] == make){
                    if(!modelList.contains(tokens[2])){
                        modelList.add(tokens[2]);
                    }
                }
            }
            Collections.sort(modelList);
        } catch (IOException e){
            Log.wtf("CarbonFootprintReader", "Error reading vehicles database on line " + line, e);
            e.printStackTrace();
        }
    }

    public void makeCar(){
        //Read the data - Maybe make this setters.
    }

    private void setupSpinners() {
        Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.i("CarbonFootprintTracker", "Testing");
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setSelection(yearSelected);
        yearSpinner.setOnItemSelectedListener(new yearSpinnerActivity());//Problem line
    }

    private void updateSpinners() {
        Spinner makeSpinner = (Spinner) findViewById(R.id.makeSpinner);
        makeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, makeList);
        makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeSpinner.setOnItemSelectedListener(new makeSpinnerActivity());
        makeSpinner.setAdapter(makeAdapter);
        makeSpinner.setSelection(makeSelected);

        Spinner modelSpinner = (Spinner) findViewById(R.id.modelSpinner);
        modelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modelList);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSpinner.setOnItemSelectedListener(new modelSpinnerActivity());
        modelSpinner.setAdapter(modelAdapter);
        modelSpinner.setSelection(modelSelected);
    }

    private class yearSpinnerActivity implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            //yearSelected = 1984+position;
            setupSpinners();
        }
        @Override
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }
    private class makeSpinnerActivity implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            //getModelData(yearSelected, "Toyota");
            //updateSpinners();
        }
        @Override
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }
    private class modelSpinnerActivity implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {

        }
        @Override
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }
    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCar.class);
    }
}
