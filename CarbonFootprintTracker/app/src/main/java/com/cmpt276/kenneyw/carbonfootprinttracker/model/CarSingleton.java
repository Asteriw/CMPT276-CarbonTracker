package com.cmpt276.kenneyw.carbonfootprinttracker.model;

/**
 * Creates a CarSingleton
 */

public class CarSingleton extends Car {

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
        walk=false;
        bus=false;
        skytrain=false;

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


    public String getGasType(){
        return gasType;
    }
    public void setGasType(String gasType){
        this.gasType = gasType;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getMake(){
        return make;
    }
    public void setMake(String make){
        this.make = make;
    }

    public String getModel(){
        return model;
    }
    public void setModel(String model){
        this.model = model;
    }

    public int getYear(){
        return year;
    }
    public void setYear(int year){
        this.year = year;
    }

    public double getHighwayEmissions(){
        return Emissions[0];
    }
    public void setHighwayEmissions(double highway){
        this.Emissions[0] = highway;
    }

    public double getCityEmissions(){
        return Emissions[1];
    }
    public void setCityEmissions(double city){
        this.Emissions[1] = city;
    }

    public boolean getWalk(){
        return walk;
    }
    public boolean getBus(){
        return bus;
    }
    public boolean getSkytrain(){
        return skytrain;
    }

    public void setWalk(boolean walk) {this.walk = walk; }
    public void setSkytrain(boolean skytrain) {
        this.skytrain = skytrain;
    }
    public void setBus(boolean bus) {
        this.bus = bus;
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
    private boolean walk;
    private boolean bus;
    private boolean skytrain;
}