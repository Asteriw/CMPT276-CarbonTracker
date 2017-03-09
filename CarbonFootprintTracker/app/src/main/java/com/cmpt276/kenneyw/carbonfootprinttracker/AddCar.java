package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

/*
* CLASS DESCRIPTION:
*  Class for the addCar activity. This reads data from provided CSV and populates it in the spinners.
*  Also provides logic for the creation of a car, calling on Car.java and CarCollection.Java
* */

public class AddCar extends AppCompatActivity{

    private ArrayList<Integer> yearList = new ArrayList<>();
    private ArrayList<String> makeList = new ArrayList<>();
    private ArrayList<String> modelList = new ArrayList<>();

    int yearSelected;
    int makeSelected;
    int modelSelected;
    String makeSelected_str;
    String modelSelected_str;
    CarCollection listOfCars = new CarCollection();
    String[] carList;

    ArrayAdapter<String> makeAdapter;
    ArrayAdapter<String> modelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        getYearData();
        setupYearSpinner();
        setUpCancelButton();
    }

    private void setUpCancelButton() {
        Button cancel_button = (Button) findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private void getCarData(int year, String make, String model) {
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

                if(Integer.parseInt(tokens[20]) == year && tokens[1].contentEquals(make) && tokens[2].contentEquals(model)) {
                    Car tempCar = new Car();
                    listOfCars=new CarCollection();
                    tempCar.setMake(tokens[1]);
                    Log.i("setMake = ", tokens[1]);
                    tempCar.setModel(tokens[2]);
                    Log.i("setModel = ", tokens[2]);
                    tempCar.setLiterEngine(Double.parseDouble(tokens[15]));
                    Log.i("setLiterEngine = ", "" + Double.parseDouble(tokens[15]));
                    tempCar.setTransmission(tokens[18]);
                    Log.i("setTransmission = ", tokens[18]);
                    tempCar.setYear(year);
                    tempCar.setName("Default Name");
                    tempCar.setGasType(tokens[16]);
                    if (Double.parseDouble(tokens[3]) == 0.0) {
                        if ((Double.parseDouble(tokens[6]) == 0.0)) {
                            tempCar.setCityEmissions(Double.parseDouble(tokens[9]));
                            tempCar.setHighwayEmissions(Double.parseDouble(tokens[10]));
                        }
                        else {
                            tempCar.setCityEmissions(Double.parseDouble(tokens[6]));
                            tempCar.setHighwayEmissions(Double.parseDouble(tokens[7]));
                        }
                    }else {
                        tempCar.setCityEmissions(Double.parseDouble(tokens[3]));
                        tempCar.setHighwayEmissions(Double.parseDouble(tokens[4]));
                    }
                    Log.i("setHighwayEmissions = ", "" + Double.parseDouble(tokens[4]));
                    Log.i("setCityEmissions = ", "" + Double.parseDouble(tokens[3]));
                    listOfCars.addCar(tempCar);
                    setupDataListView();
                }
            }
        } catch (IOException e){
            Log.wtf("CarbonFootprintReader", "Error reading vehicles database on line " + line, e);
            e.printStackTrace();
        }
    }

    private void setupDataListView() {
        carList = listOfCars.getCarsDescriptions();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listview_layout, carList);
        ListView list = (ListView) findViewById(R.id.car_listview);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                // display a dialog, get name from user
                FragmentManager manager = getSupportFragmentManager();
                GetCarNameFragment dialog = new GetCarNameFragment();
                dialog.show(manager,"Enter Car Name");
                Log.i("TAG", "Launched GetCarNameFragment Dialog");

                Log.i("Final name = ", listOfCars.getCar(position).getName());
/*
                    // Check if name was set properly
                    Intent intent2SelectTransportation = SelectTransportation.makeIntent(AddCar.this);
                    intent2SelectTransportation.putExtra("CarDataName", listOfCars.getCar(position).getName());
                    intent2SelectTransportation.putExtra("CarDataCityEmissions", listOfCars.getCar(position).getCityEmissions());
                    intent2SelectTransportation.putExtra("CarDataGasType", listOfCars.getCar(position).getGasType());
                    intent2SelectTransportation.putExtra("CarDataHighwayEmissions", listOfCars.getCar(position).getHighwayEmissions());
                    intent2SelectTransportation.putExtra("CarDataLiterEngine", listOfCars.getCar(position).getLiterEngine());
                    intent2SelectTransportation.putExtra("CarDataMake", listOfCars.getCar(position).getMake());
                    intent2SelectTransportation.putExtra("CarDataModel", listOfCars.getCar(position).getModel());
                    intent2SelectTransportation.putExtra("CarDataTransmission", listOfCars.getCar(position).getTransmission());
                    intent2SelectTransportation.putExtra("CarDataYear", listOfCars.getCar(position).getYear());
                    startActivity(intent2SelectTransportation);
*/
            }
        });

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

            getCarData(yearSelected, makeSelected_str, modelSelected_str);
        }
        @Override
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddCar.class);
    }
}
