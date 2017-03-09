package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
/*
* CLASS DESCRIPTION:
*  This is the Class for the SelectTransportation activity. THis class allows a user to select a car from their list of cars,
*  and create a car if they so choose. Relies on CarCollection and Car.java
* */
public class SelectTransportation extends AppCompatActivity {

    private static final String SHAREDPREF_SET = "CarbonFootprintTracker";
    private static final String SHAREDPREF_ITEM_CARNAME = "CarName";
    private static final String SHAREDPREF_ITEM_CARARRAY = "CarArray";
    private static final String SHAREDPREF_ITEM_AMOUNTOFCARS = "AmountOfCars";
    int carAmount = 0;

    //CarCollection carList = new CarCollection();
    ArrayAdapter<String> adapter;
    CarCollection cars = new CarCollection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);

        //loadPreferences();
        setupAddCarButton();
        setupCancelButton();
        setupListView();
        registerForContextMenu();
        //setupRouteButton();
    }
    /*
    private void saveDataInIntent(Intent i,int position){
       // i.putExtra();
    }
    private void loadPreferences() {
        SharedPreferences preferences = getSharedPreferences(SHAREDPREF_SET, MODE_PRIVATE);
        carAmount = preferences.getInt(SHAREDPREF_ITEM_AMOUNTOFCARS, 6);
        String tempCarArray = preferences.getString(SHAREDPREF_ITEM_CARARRAY, "");
        carArray = tempCarArray.split(",");
    }

    private void savePreferences(){
        SharedPreferences preferences = getSharedPreferences(SHAREDPREF_SET, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SHAREDPREF_ITEM_AMOUNTOFCARS, carAmount);
        StringBuilder tempArray = new StringBuilder();
        String[] tempListOfCars = carList.getCarsNames();
        for (int i = 0; i < carList.countCars(); i++){
            tempArray.append(tempListOfCars[i]).append(",");
        }
        editor.putString(SHAREDPREF_ITEM_CARARRAY, tempArray.toString());
        editor.apply();
    }
*/
    private void setupListView() {
        Intent intent = getIntent();
        Car car = new Car();
        cars = new CarCollection();
        car.setName( intent.getStringExtra("CarDataName" ));
        car.setYear( intent.getIntExtra("CarDataYear",0 ));
        car.setTransmission( intent.getStringExtra("CarDateTransmission" ));
        car.setCityEmissions( intent.getDoubleExtra("CarDataCityEmissions", 0 ));
        car.setGasType( intent.getStringExtra("CarDataGasType" ));
        car.setHighwayEmissions( intent.getDoubleExtra("CarDataHighwayEmissions", 0 ));
        car.setLiterEngine( intent.getDoubleExtra("CarDataLiterEngine", 0 ));
        car.setMake( intent.getStringExtra("CarDataMake" ));
        car.setModel( intent.getStringExtra("CarDataModel"));
        cars.addCar(car);

        //build adapter
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.listview_layout,
                cars.getCarsDescriptionsWithName());
       
        // Configure the list view
        ListView list = (ListView) findViewById(R.id.car_listview);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=SelectRoute.makeIntent(SelectTransportation.this);
                i.putExtra("carName",cars.getCar(position).getName());
                i.putExtra("carMake",cars.getCar(position).getMake());
                i.putExtra("carModel",cars.getCar(position).getModel());
                i.putExtra("carYear",cars.getCar(position).getYear());
                i.putExtra("MPGCity",cars.getCar(position).getCityEmissions());
                i.putExtra("MPGHighway",cars.getCar(position).getHighwayEmissions());
                i.putExtra("carGasType",cars.getCar(position).getGasType());
                startActivity(i);
            }
        });
    }

    private void registerForContextMenu(){
        ListView list = (ListView) findViewById(R.id.car_listview);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        // Redirect the user to the SelectRoute Screen
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent selectTransportation2SelectRoute = new Intent(getApplicationContext(), SelectRoute.class);
                startActivity(selectTransportation2SelectRoute);
            }
        });
    }

    private void setupAddCarButton() {
        Button RouteButton = (Button) findViewById(R.id.btn_add_car);
        RouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SelectRouteIntent = AddCar.makeIntent(SelectTransportation.this);
                startActivity(SelectRouteIntent);
            }
        });
    }

    private void setupCancelButton() {
        Button CancelButton = (Button) findViewById(R.id.cancel_transport);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportation.class);
    }
}