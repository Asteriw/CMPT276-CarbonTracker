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
    String makeSelected_str;
    String modelSelected_str;

    ArrayAdapter<String> makeAdapter;
    ArrayAdapter<String> modelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        getYearData();
        setupYearSpinner();
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

        setupMakeSpinner();
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
                if(Integer.parseInt(tokens[20]) == year && tokens[1].contentEquals(make)){
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

        setupModelSpinner();
    }

    public void makeCar(){
        //Read the data - Maybe make this setters.
    }

    private void setupYearSpinner() {
        Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.i("CarbonFootprintTracker", "Testing");
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setSelection(yearSelected);
        yearSpinner.setOnItemSelectedListener(new yearSpinnerActivity());//Problem line
    }

    private void setupMakeSpinner() {
        Spinner makeSpinner = (Spinner) findViewById(R.id.makeSpinner);
        makeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, makeList);
        makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeSpinner.setAdapter(makeAdapter);
        makeSpinner.setSelection(makeSelected);
        makeSpinner.setOnItemSelectedListener(new makeSpinnerActivity());
    }

    private void setupModelSpinner(){
        Spinner modelSpinner = (Spinner) findViewById(R.id.modelSpinner);
        modelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modelList);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSpinner.setAdapter(modelAdapter);
        modelSpinner.setSelection(modelSelected);
        modelSpinner.setOnItemSelectedListener(new modelSpinnerActivity());
    }

    private class yearSpinnerActivity implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            yearSelected = Integer.parseInt(parent.getItemAtPosition(position).toString());
            Log.i("yearSelected = ", "" + yearSelected);
            getMakeData(yearSelected);
        }
        @Override
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }

    private class makeSpinnerActivity implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
             makeSelected = position;
             makeSelected_str = parent.getItemAtPosition(position).toString();
             Log.i("makeSelected = ", "" + makeSelected);
             Log.i("makeSelected_str = ", makeSelected_str);
             getModelData(yearSelected, makeSelected_str);
        }
        @Override
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }

    private class modelSpinnerActivity implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            modelSelected = position;
            modelSelected_str = parent.getItemAtPosition(position).toString();
            Log.i("modelSelected = ", "" + modelSelected);
            Log.i("modelSelected_str = ", modelSelected_str);
        }
        @Override
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCar.class);
    }
}
