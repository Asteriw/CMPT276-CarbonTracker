package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
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

    CarCollection CarList;
    private List<Car> carList = new ArrayList<>();
    String[] arrayOfCars = {"temporary"};
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);

        setupAddCarButton();
        setupCancelButton();
        setupListView();
        registerForContextMenu();
        //setupRouteButton();
    }

    private void setupListView() {
        //build adapter
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.listview_layout,
                arrayOfCars);
        // Configure the list view
        ListView list = (ListView) findViewById(R.id.car_listview);
        list.setAdapter(adapter);
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