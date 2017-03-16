package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
    CarCollection carList = new CarCollection();
    int makeSelected = 0;
    int modelSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        openDatabase();
        setupYearSpinner();
        setupBackButton();
        populateListView();
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
            selectedYear = parent.getItemAtPosition(position).toString();
            Log.i("This", selectedYear);
            setupMakeSpinner(selectedYear);
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
            selectedMake = parent.getItemAtPosition(position).toString();
            Log.i("This", selectedMake);
            setupModelSpinner(selectedYear, selectedMake);
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
            selectedModel = parent.getItemAtPosition(position).toString();
            Log.i("This", selectedModel);
            makeCarList(selectedYear, selectedMake, selectedModel);
        }
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }

    private void makeCarList(String yearSelected, String makeSelected, String modelSelected) {
        carList = new CarCollection();
        List<String[]> tempCarList = DatabaseAccess.getInstance(this).getTempCarList(yearSelected, makeSelected, modelSelected);
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        Log.i("this", "Database query passed");
        for(int i = 0; i<tempCarList.size(); i++){
            String[] carData = tempCarList.get(i);
            Car car = new Car();
            car.setMake(carData[2]);
            car.setModel(carData[3]);
            car.setYear(Integer.parseInt(carData[21]));
            car.setYear(Integer.parseInt(carData[21]));
            car.setTransmission(carData[19]);
            car.setLiterEngine(roundToTwoDecimals(Double.parseDouble(carData[16])));
            if (carData[4] == "0") {
                if (carData[7] == "0") {
                    car.setCityEmissions(Double.parseDouble(carData[10]));
                    car.setHighwayEmissions(Double.parseDouble(carData[11]));
                } else {
                    car.setCityEmissions(Double.parseDouble(carData[7]));
                    car.setHighwayEmissions(Double.parseDouble(carData[8]));
                }
            } else {
                car.setCityEmissions(Double.parseDouble(carData[4]));
                car.setHighwayEmissions(Double.parseDouble(carData[5]));
            }
            carList.addCar(car);
        }
        populateListView();
    }

    private void populateListView() {
        ListView listView = (ListView) findViewById(R.id.car_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.layout_for_list, carList.getCarsDescriptions());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                EditText inputName = (EditText) findViewById(R.id.nick_name_from_user);
                String userInputName = inputName.getText().toString();
                if (userInputName == ""){
                    Toast.makeText(AddCar.this, R.string.error_toast, Toast.LENGTH_SHORT).show();
                } else {
                    CarSingleton masterCar = CarSingleton.getInstance();
                }
            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu, menu);
    }

    private double roundToTwoDecimals(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tempValue = Math.round(value);
        return (double) tempValue/factor;
    }

    private void closeDatabase() {
        databaseAccess.close();
    }

    private void openDatabase() {
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
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