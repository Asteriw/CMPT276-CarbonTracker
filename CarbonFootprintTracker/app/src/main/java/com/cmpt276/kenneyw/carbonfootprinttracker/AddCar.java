package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    private ArrayList<Integer> yearList = new ArrayList<>(); //List of years queried from the CSV
    private ArrayList<String> makeList = new ArrayList<>();  //List of makes queried from the CSV
    private ArrayList<String> modelList = new ArrayList<>(); //List of models queried from the CSV
    int yearSelected; //Selected from dropdown
    int makeSelected; //Selected from dropdown
    int modelSelected;//Selected from dropdown
    ArrayAdapter<String> makeAdapter;     //Adapters for the spinners
    ArrayAdapter<String> modelAdapter;
    ArrayAdapter<String> listViewAdapter;
    CarCollection potentialCarList;       //Car list to populate in the list view. generated from 3rd and final database query.
    String[] listOfCars; //Above car list in a string for the list view. to be gotten from potentialCarList.getCarsDescription

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        getYearData();
        setupSpinners();

        //setupRouteButton();
    }

    private void setupListView() { //Updates the listView.
        listOfCars = potentialCarList.getCarsDescriptions();
        listViewAdapter = new ArrayAdapter<>(this, R.layout.listview_layout, listOfCars);
        ListView list = (ListView) findViewById(R.id.car_listview);
        list.setAdapter(listViewAdapter);
        registerForContextMenu(list);
    }

    private void getYearData() { //Query the CSV to get all years.
        InputStream stream = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            //Step over headers
            reader.readLine(); //Skip first list
            while ((line = reader.readLine()) != null) { //While not EOF...
                //Split by ','
                String[] tokens = line.split(",");       //Take the entire row
                if(!yearList.contains(Integer.parseInt(tokens[20]))){//take the 20th value of that row (which happens to the year)
                    yearList.add(Integer.parseInt(tokens[20])); //and put it in our yearList if it's not already there.
                }
            }
            Collections.sort(yearList);
        } catch (IOException e){
            Log.wtf("CarbonFootprintReader", "Error reading vehicles database on line " + line, e);
            e.printStackTrace();
        }
    }

    private void getMakeData(int year) { //Similar to above function
        InputStream stream = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            //Step over headers
            reader.readLine(); //Skip first list
            while ((line = reader.readLine()) != null) { //While not EOF...
                //Split by ','
                String[] tokens = line.split(",");   //Take the entire row
                if(Integer.parseInt(tokens[20]) == year){ //If the year of the row is the year given by the user
                    if(!makeList.contains(tokens[1])){ //and the make isn't already in the list...
                        makeList.add(tokens[1]);     //Store the make.
                    }
                }
            }
            Collections.sort(makeList);
        } catch (IOException e){
            Log.wtf("CarbonFootprintReader", "Error reading vehicles database on line " + line, e);
            e.printStackTrace();
        }
    }

    private void getModelData(int year, String make) { //Similar to above function
        InputStream stream = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            //Step over headers
            reader.readLine(); //Skip first list
            while ((line = reader.readLine()) != null) {//While not EOF...
                //Split by ','
                String[] tokens = line.split(","); //Take the entire row
                if(Integer.parseInt(tokens[20]) == year && tokens[1] == make){ //If the year of the row is the year given by the user and the make is equal to the user make...
                    if(!modelList.contains(tokens[2])){ //and the model isn't already in the list...
                        modelList.add(tokens[2]); //Store the make
                    }
                }
            }
            Collections.sort(modelList);
        } catch (IOException e){
            Log.wtf("CarbonFootprintReader", "Error reading vehicles database on line " + line, e);
            e.printStackTrace();
        }
    }

    public void makeCar(String userName, String name, String make, String model, int highwayEmissions, int cityEmissions, int year){
        if(userName != ""){
            car = new Car(userName, name, make, model, highwayEmissions, cityEmissions, year);
        }
        //Read the data - Maybe make this setters.
    }

    private void setupSpinners() {
        Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setSelection(yearSelected);
        yearSpinner.setOnItemSelectedListener(new yearSpinnerActivity());
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
            getMakeData(1984+position);
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
