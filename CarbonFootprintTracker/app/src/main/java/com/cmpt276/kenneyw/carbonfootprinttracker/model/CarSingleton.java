package com.cmpt276.kenneyw.carbonfootprinttracker.model;

import com.cmpt276.kenneyw.carbonfootprinttracker.R;

/**
 * Creates a CarSingleton. This is just like a normal car, expect there can be only one.
 * The singleton will keep it's values somewhat persistently.
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
        bike=false;
        bus=false;
        skytrain=false;
        iconID = R.drawable.car_icon_2;
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

    public int getIconID(){return iconID;}
    public void setIconID(int iconId){
        if (iconId == R.drawable.car_icon_2)
            this.iconID = R.drawable.car_icon_2;
        else if (iconId == R.drawable.truck)
            this.iconID = R.drawable.truck;
        else if (iconId == R.drawable.modern)
            this.iconID = R.drawable.modern;
        else if (iconId == R.drawable.sport)
            this.iconID = R.drawable.sport;
        else if (iconId == R.drawable.classic)
            this.iconID = R.drawable.classic;

        else if (iconId == R.drawable.bike_icon)
            this.iconID = R.drawable.bike_icon;
        else if (iconId == R.drawable.walk_icon)
            this.iconID = R.drawable.walk_icon;
        else if (iconId == R.drawable.bus_icon)
            this.iconID = R.drawable.bus_icon;
        else{
            this.iconID = R.drawable.train_icon;
        }
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
    public boolean getBike(){return bike;}
    public void setWalk(boolean walk) {this.walk = walk; }
    public void setSkytrain(boolean skytrain) {
        this.skytrain = skytrain;
    }
    public void setBus(boolean bus) {
        this.bus = bus;
    }
    public void setBike(boolean bike){this.bike = bike;}

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
    private boolean bike;
    private boolean skytrain;
    private int iconID;
}