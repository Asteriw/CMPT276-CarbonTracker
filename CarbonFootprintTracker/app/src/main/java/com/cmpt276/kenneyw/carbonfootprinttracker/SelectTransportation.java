package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SelectTransportation extends AppCompatActivity {

    Car car;
    private List<Car> carList = new ArrayList<>();
    private ArrayList<Integer> yearList = new ArrayList<>();
    private ArrayList<String> makeList = new ArrayList<>();
    private ArrayList<String> modelList = new ArrayList<>();
    DBAdapter database;
    int yearSelected;
    int makeSelected;
    int modelSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);

        openDatabase();
        setupRouteButton();
        setupDatabase();
        queryDatabase();
        setupSpinners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDatabase();
    }

    private void openDatabase() {
        database = new DBAdapter(this);
        database.open();
    }
    private void closeDatabase() {
        database.close();
    }


    private void setupRouteButton() {
        Button RouteButton = (Button) findViewById(R.id.btnselectroute);
        RouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SelectRouteIntent = SelectRoute.makeIntent(SelectTransportation.this);
                startActivity(SelectRouteIntent);
            }
        });
    }

    private void setupDatabase() {
        InputStream stream = getResources().openRawResource(R.raw.cardb);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, Charset.forName("UTF-8"))
        );
        String line = "";
        File path = getDatabasePath("CarDB");
        if (path.exists()){
            Log.i("CarbonFootprintTracker", "File exists!");
        } else {
            try {
                //Step over headers
                reader.readLine();
                while ((line = reader.readLine()) != null) {
                    //Split by ','
                    String[] tokens = line.split(",");
                    Log.i("CarbonFootprintTracker", "" + tokens[0] + " ");
                    long newID = database.insertRow(
                            Integer.parseInt(tokens[0]),
                            tokens[1],
                            tokens[2],
                            Float.parseFloat(tokens[3]),
                            Float.parseFloat(tokens[4]),
                            Float.parseFloat(tokens[5]),
                            Float.parseFloat(tokens[6]),
                            Float.parseFloat(tokens[7]),
                            Float.parseFloat(tokens[8]),
                            Float.parseFloat(tokens[9]),
                            Float.parseFloat(tokens[10]),
                            Float.parseFloat(tokens[11]),
                            Integer.parseInt(tokens[12]),
                            Float.parseFloat(tokens[13]),
                            Integer.parseInt(tokens[14]),
                            Float.parseFloat(tokens[15]),
                            tokens[16],
                            tokens[17],
                            tokens[18],
                            tokens[19],
                            Integer.parseInt(tokens[20]),
                            tokens[21],
                            tokens[22],
                            tokens[23]
                    );
                }
                Log.i("CarbonFootprintReader", "Created database");
            } catch (IOException e) {
                Log.wtf("CarbonFootprintReader", "Error reading vehicles database on line " + line, e);
                e.printStackTrace();
            }
        }
    }

    private void queryDatabase() {
        File path = getDatabasePath("CarDB");
        if (path.exists()){
            yearList = database.getYearValues();
            makeList = database.getMakeValues();
            modelList = database.getModelValues();
        }
    }

    private void setupSpinners() {
        Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        ArrayAdapter<CharSequence> yearAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setOnItemSelectedListener(new SpinnerListener1());
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setSelection(yearSelected);

        Spinner makeSpinner = (Spinner) findViewById(R.id.makeSpinner);
        ArrayAdapter<CharSequence> makeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, makeList);
        makeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        makeSpinner.setOnItemSelectedListener(new SpinnerListener2());
        makeSpinner.setAdapter(makeAdapter);
        makeSpinner.setSelection(makeSelected);

        Spinner modelSpinner = (Spinner) findViewById(R.id.modelSpinner);
        ArrayAdapter<CharSequence> modelAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, modelList);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modelSpinner.setOnItemSelectedListener(new SpinnerListener3());
        modelSpinner.setAdapter(modelAdapter);
        modelSpinner.setSelection(modelSelected);
    }

    private class SpinnerListener1 implements AdapterView.OnItemSelectedListener {
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {

        }
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }
    private class SpinnerListener2 implements AdapterView.OnItemSelectedListener {
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {

        }
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }
    private class SpinnerListener3 implements AdapterView.OnItemSelectedListener {
        public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {

        }
        public void onNothingSelected (AdapterView<?> parent) {
        }
    }



    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportation.class);
    }
}
