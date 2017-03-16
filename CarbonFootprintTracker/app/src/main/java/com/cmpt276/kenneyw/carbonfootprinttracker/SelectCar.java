package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/*
*   This class shows the user a list of cars (with engine displacement in L and transmission)
*   When user clicks an item on the list, it directs the user to "Select Route" screen
*   User is allowed to add/edit/delete
* */

public class SelectCar extends AppCompatActivity {

    CarCollection myCars = new CarCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);
        setupAddCarButton();
        setupBackButton();
        setCarList();
        registerClickCallBack();
    }

    private void setupAddCarButton() {
        // Direct to "AddCar" screen
        Button add_button = (Button) findViewById(R.id.add_car_button_select_car);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SelectCar2AddCar = AddCar.makeIntent(SelectCar.this);
                startActivity(SelectCar2AddCar);
            }
        });
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                editEntry(info.position);
                return true;
            case R.id.delete:
                deleteEntry(info.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void editEntry(int position) {//TODO

    }

    private void deleteEntry(int position) {
        myCars.deleteCar(position);
    }

    private void setupBackButton() {
        Button back_button = (Button) findViewById(R.id.back_button_select_car);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setCarList() {
        // Create list of items

        // Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.layout_for_list, myCars.getCarsDescriptionsWithName());

        // Configure the list view
        ListView list = (ListView) findViewById(R.id.carlist);
        list.setAdapter(adapter);
        registerForContextMenu(list);
    }

    // Upon a click on an item in the list, direct to "Select Route"
    private void registerClickCallBack() {
        ListView list = (ListView) findViewById(R.id.carlist);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent SelectCar2SelectRoute = SelectRoute.makeIntent(SelectCar.this);
                startActivity(SelectCar2SelectRoute);
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectCar.class);
    }
}