package com.cmpt276.kenneyw.carbonfootprinttracker;

/**
 * Creates a CarSingleton
 */

public class CarSingleton {

    private static CarSingleton carinstance = null;

    private CarSingleton(){
        name = "";
        make = "";
        model = "";
        Emissions[0] = 0;
        Emissions[1] = 0;
        year = 0;
        transmission = "";
        literEngine = 0;
    }

    public static CarSingleton getInstance(){
        if(carinstance == null){
            carinstance = new CarSingleton();
        }
        return carinstance;
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

    // Variables Declaration
    private String name;
    private String make;
    private String gasType;
    private int year;
    private String model;
    private double[] Emissions =  new double[2];
    private String transmission;
    private double literEngine;
}