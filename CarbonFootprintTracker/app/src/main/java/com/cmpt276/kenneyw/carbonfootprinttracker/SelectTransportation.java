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

public class SelectTransportation extends AppCompatActivity {

    private static final String SHAREDPREF_SET = "CarbonFootprintTracker";
    private static final String SHAREDPREF_ITEM_CARNAME = "CarName";
    private static final String SHAREDPREF_ITEM_CARARRAY = "CarArray";
    private static final String SHAREDPREF_ITEM_AMOUNTOFCARS = "AmountOfCars";
    int carAmount = 0;
    String[] carArray;
    CarCollection carList = new CarCollection();
    String[] arrayOfCars = {};
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);

        //loadPreferences();
        setupAddCarButton();
        setupCancelButton();
        setupListView();
        //setupRouteButton();
    }
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

    private void setupListView() {
        adapter = new ArrayAdapter<>(this, R.layout.listview_layout, arrayOfCars);
        ListView list = (ListView) findViewById(R.id.car_listview);
        list.setAdapter(adapter);
        registerForContextMenu(list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i= SelectRoute.makeIntent(SelectTransportation.this);
                saveDataInIntent(i,position);
                startActivity(i);
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