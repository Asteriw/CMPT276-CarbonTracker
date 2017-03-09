package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
/*
* CLASS DESCRIPTION:
*  Class for the class for the Car object. Creates a Car from the CSV values and user input. If user inputs an empty name,
*  "Default Name" will be set as the car name.
* */

public class Car extends AppCompatActivity {
    private String name;
    private String make;
    private String gasType;
    private int year;
    private String model;
    private double[] Emissions =  new double[2];
    private String transmission;
    private double literEngine;

    Car(String name_temp, String make_temp, String model_temp, double highway_temp, double city_temp, int year_temp, String transmission_temp, double literEngine_temp ){
        name = name_temp;
        make = make_temp;
        model = model_temp;
        Emissions[0] = highway_temp;
        Emissions[1] = city_temp;
        year = year_temp;
        transmission = transmission_temp;
        literEngine = literEngine_temp;
    }

    Car(){
        name = "";
        make = "";
        model = "";
        Emissions[0] = 0;
        Emissions[1] = 0;
        year = 0;
        transmission = "";
        literEngine = 0;
    }

    public double getLiterEngine() {
        return literEngine;
    }

    public void setLiterEngine(double literEngine) {
        this.literEngine = literEngine;
    }
    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }


    String getGasType(){
        return gasType;
    }
    void setGasType(String gasType){
        this.gasType = gasType;
    }

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

    double getHighwayEmissions(){
        return Emissions[0];
    }
    void setHighwayEmissions(double highway){
        this.Emissions[0] = highway;
    }

    double getCityEmissions(){
        return Emissions[1];
    }
    void setCityEmissions(double city){
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
