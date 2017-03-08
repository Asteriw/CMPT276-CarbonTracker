package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SelectTransportation extends AppCompatActivity {

    CarCollection CarList;
    private List<Car> carList = new ArrayList<>();
    String[] arrayOfCars = {};
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);

        setupAddCarButton();
        setupCancelButton();
        setupListView();
        //setupRouteButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupListView() {
        adapter = new ArrayAdapter<>(this, R.layout.listview_layout, arrayOfCars);
        ListView list = (ListView) findViewById(R.id.car_listview);
        list.setAdapter(adapter);
        registerForContextMenu(list);
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