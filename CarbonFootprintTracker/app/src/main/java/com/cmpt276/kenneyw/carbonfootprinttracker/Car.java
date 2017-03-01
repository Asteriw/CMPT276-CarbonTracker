package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;

public class Car extends AppCompatActivity {
    private String name;
    private String make;
    private int year;
    private String model;
    private int[] Emissions =  new int[2];

    Car(String name, String make, String model, int highway, int city, int year){
        this.name = name;
        this.make = make;
        this.model = model;
        this.Emissions[0] = highway;
        this.Emissions[1] = city;
        this.year = year;
    }

    Car(){}

    String getName(){
        return name;
    }
    void setName(String name){
        this.name = name;
    }

    String getMake(){
        return make;
    }
    void setMake(String make){
        this.make = make;
    }

    String getModel(){
        return model;
    }
    void setModel(String model){
        this.model = model;
    }

    int getYear(){
        return year;
    }
    void setYear(int year){
        this.year = year;
    }

    int getHighwayEmissions(){
        return Emissions[0];
    }
    void setHighwayEmissions(int highway){
        this.Emissions[0] = highway;
    }

    int getCityEmissions(){
        return Emissions[1];
    }
    void setCityEmissions(int city){
        this.Emissions[1] = city;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", make='" + make + '\'' +
                ", year=" + year +
                ", model='" + model + '\'' +
                ", Emissions=" + Arrays.toString(Emissions) +
                '}';
    }
}
