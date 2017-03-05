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
import android.widget.Button;

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
    DBAdapter database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);

        openDatabase();
        setupRouteButton();
        setupDatabase();
        queryDatabase();
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
        InputStream stream = getResources().openRawResource(R.raw.vehicles);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, Charset.forName("UTF-8"))
        );
        String line = "";
        File path = getDatabasePath("CarDB");
        if (path.exists()){
            Log.i("CarbonFootprintTracker", "File exists!");
        } else {
            /*try {
                SQLiteDatabase checkDB = null;
                checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null,
                        SQLiteDatabase.OPEN_READONLY);
                checkDB.close();
            } catch (SQLiteException e) {*/
                // database doesn't exist yet.
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
        ArrayList<Integer> temp = database.getYearValues();
        Log.i("CarbonFootprintTracker", temp.toString());
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportation.class);
    }
}
