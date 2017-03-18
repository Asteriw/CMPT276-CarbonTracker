package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.support.v7.app.AppCompatActivity;

import java.util.Date;

public class Journey extends AppCompatActivity {
/*
Stores a Journey, and calculates CO2 emitted via this Journey in constructor and in seperate function to be called
when editing a pre-existing Journey.
 */
    private String routeName;
    private int cityDistance;
    private int highwayDistance;
    private String name;
    private String gasType;
    private double mpgCity;
    private double mpgHighway;
    private String transmission;
    private double literEngine;
    private Date dateOfTravel;
    private double totalEmissions;
    private boolean Bike;
    private boolean Bus;
    private boolean Skytrain;

    public Journey(String routeName, int cityDistance, int highwayDistance,
                   String carName, String gasType, double mpgCity, double mpgHighway, double literEngine,
                   Date dateOfTravel,boolean bus,boolean bike,boolean skytrain){
        this.routeName=routeName;
        this.cityDistance = cityDistance;
        this.highwayDistance = highwayDistance;
        this.name=carName;
        this.gasType=gasType;
        this.mpgCity=mpgCity;
        this.mpgHighway=mpgHighway;
        this.literEngine=literEngine;
        this.dateOfTravel=dateOfTravel;
        this.Bus=bus;
        this.Bike=bike;
        this.Skytrain=skytrain;
        if(!bus &&!bike&& !skytrain) {
            Calculation c = new Calculation();
            switch (gasType) {
                case "Premium":
                    this.totalEmissions += c.calculateCO2Diesel(mpgCity, literEngine, cityDistance);
                    this.totalEmissions += c.calculateCO2Diesel(mpgHighway, literEngine, highwayDistance);
                    break;
                case "Regular":
                    this.totalEmissions += c.calculateCO2Gasoline(mpgCity, literEngine, cityDistance);
                    this.totalEmissions += c.calculateCO2Gasoline(mpgHighway, literEngine, highwayDistance);
                    break;
                default:
                    this.totalEmissions = 0;
                    break;
            }
        }

        else if(bike){
            this.totalEmissions=0;
        }
        else if(skytrain){
            this.totalEmissions=0;
        }
        else{
            this.totalEmissions=0.89*(this.cityDistance+this.highwayDistance);
        }
    }

    public double CalculateTotalEmissions(){
        Calculation c=new Calculation();
        double totalEmissions=0;
        if(!this.Bus && !this.Bike && !this.Skytrain )
        switch(gasType) {               //CONFIRM
            case "Premium":
                totalEmissions += c.calculateCO2Diesel(mpgCity,literEngine,cityDistance);
                totalEmissions += c.calculateCO2Diesel(mpgHighway,literEngine,highwayDistance);
                break;
            case "Regular":             //CONFIRM
                totalEmissions += c.calculateCO2Gasoline(mpgCity,literEngine,cityDistance);
                totalEmissions += c.calculateCO2Gasoline(mpgHighway,literEngine,highwayDistance);
                break;
        }
        if(this.Skytrain){
            totalEmissions=0.89*(this.cityDistance+this.highwayDistance);
        }
        return totalEmissions;

    }

    public int getCityDistance() {
        return cityDistance;
    }

    public void setCityDistance(int cityDistance) {
        this.cityDistance = cityDistance;
    }

    public String getGasType() {
        return gasType;
    }

    public void setGasType(String gasType) {
        this.gasType = gasType;
    }

    public int getHighwayDistance() {
        return highwayDistance;
    }

    public void setHighwayDistance(int highwayDistance) {
        this.highwayDistance = highwayDistance;
    }

    public double getLiterEngine() {
        return literEngine;
    }

    public void setLiterEngine(double literEngine) {
        this.literEngine = literEngine;
    }

    public double getMpgCity() {
        return mpgCity;
    }

    public void setMpgCity(double mpgCity) {
        this.mpgCity = mpgCity;
    }

    public double getMpgHighway() {
        return mpgHighway;
    }

    public void setMpgHighway(double mpgHighway) {
        this.mpgHighway = mpgHighway;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Date getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfTravel(Date dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public double getTotalEmissions() {
        return totalEmissions;
    }

    public void setTotalEmissions(double totalEmissions) {
        this.totalEmissions = totalEmissions;
    }

    public boolean isBike() {
        return Bike;
    }

    public void setBike(boolean bike) {
        Bike = bike;
    }

    public boolean isBus() {
        return Bus;
    }

    public void setBus(boolean bus) {
        Bus = bus;
    }

    public boolean isSkytrain() {
        return Skytrain;
    }

    public void setSkytrain(boolean skytrain) {
        Skytrain = skytrain;
    }

    public String toString() {
        return name+" - "+routeName+" - "+cityDistance+" City, "+highwayDistance+" Highway"+" Date: "+dateOfTravel;
    }

}
