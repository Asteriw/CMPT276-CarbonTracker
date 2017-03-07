package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
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
import java.util.List;

public class AddCar extends AppCompatActivity{

    Car car;
    private List<Car> carList = new ArrayList<>();
    private ArrayList<Integer> yearList = new ArrayList<>();
    private ArrayList<String> makeList = new ArrayList<>();
    private ArrayList<String> modelList = new ArrayList<>();
    int yearSelected;
    int makeSelected;
    int modelSelected;
    ArrayAdapter<CharSequence> yearAdapter;
    ArrayAdapter<CharSequence> makeAdapter;
    ArrayAdapter<CharSequence> modelAdapter;

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
        //Car car = new Car();
                /*if(tokens[0].length() > 0){
                    car.setName(tokens[0]); //Name
                } else {
                    car.setName("Untitled");
                }
                if(tokens[1].length() > 0){
                    car.setMake(tokens[1]); //Make
                } else {
                    car.setMake("Other");
                }
                if(tokens[2].length() > 0){
                    car.setModel(tokens[2]); //Model
                } else {
                    car.setModel("Other");
                }
                if(tokens[3].length() > 0){
                    car.setHighwayEmissions(Integer.parseInt(tokens[3])); //Highway Emissions
                } else {
                    car.setHighwayEmissions(1);
                }
                if(tokens[4].length() > 0){
                    car.setCityEmissions(Integer.parseInt(tokens[4])); //City Emission
                } else {
                    car.setCityEmissions(1);
                }
                if(tokens.length >=6 && tokens[5].length() > 0){
                    car.setYear(Integer.parseInt(tokens[5]));//Year
                } else {
                    car.setYear(0);
                }
                carList.add(car);

                Log.d("CarbonFootprintReader", "Just created: " + car);
                */
    }

    private void setupSpinners() {
        Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        yearAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setOnItemSelectedListener(new yearSpinnerActivity());
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setSelection(yearSelected);
    }

    private void updateSpinners() {
        Spinner makeSpinner = (Spinner) findViewById(R.id.makeSpinner);
        makeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, makeList);
        makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeSpinner.setOnItemSelectedListener(new makeSpinnerActivity());
        makeSpinner.setAdapter(makeAdapter);
        makeSpinner.setSelection(makeSelected);

        Spinner modelSpinner = (Spinner) findViewById(R.id.modelSpinner);
        modelAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, modelList);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSpinner.setOnItemSelectedListener(new modelSpinnerActivity());
        modelSpinner.setAdapter(modelAdapter);
        modelSpinner.setSelection(modelSelected);
    }

    private class yearSpinnerActivity implements AdapterView.OnItemSelectedListener {
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            //yearSelected = 1984+position;
            getMakeData(yearSelected);
            setupSpinners();
        }
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }
    private class makeSpinnerActivity implements AdapterView.OnItemSelectedListener {
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
            //getModelData(yearSelected, "Toyota");
            //updateSpinners();
        }
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }
    private class modelSpinnerActivity implements AdapterView.OnItemSelectedListener {
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {

        }
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }
    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCar.class);
    }
}
