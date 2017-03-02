package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SelectTransportation extends AppCompatActivity {

    Car car;
    private List<Car> carList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);

        setupRouteButton();
        readCarData();
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

    private void readCarData() {
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

                //Read the data - Maybe make this setters.
                Car car = new Car();
                if(tokens[0].length() > 0){
                    car.setName(tokens[0]); //Name
                } else {
                    car.setName("Untitled");
                }
                if(tokens[1].length() > 0){
                    car.setMake(tokens[1]); //Make
                } else {
                    car.setMake("Other");
                }
                if(tokens[2].length() > 0){
                    car.setModel(tokens[2]); //Model
                } else {
                    car.setModel("Other");
                }
                if(tokens[3].length() > 0){
                    car.setHighwayEmissions(Integer.parseInt(tokens[3])); //Highway Emissions
                } else {
                    car.setHighwayEmissions(1);
                }
                if(tokens[4].length() > 0){
                    car.setCityEmissions(Integer.parseInt(tokens[4])); //City Emission
                } else {
                    car.setCityEmissions(1);
                }
                if(tokens.length >=6 && tokens[5].length() > 0){
                    car.setYear(Integer.parseInt(tokens[12]));//Year
                } else {
                    car.setYear(0);
                }
                carList.add(car);

                Log.d("CarbonFootprintReader", "Just created: " + car);
            }
        } catch (IOException e){
            Log.wtf("CarbonFootprintReader", "Error reading vehicles database on line " + line, e);
            e.printStackTrace();
        }
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportation.class);
    }
}
