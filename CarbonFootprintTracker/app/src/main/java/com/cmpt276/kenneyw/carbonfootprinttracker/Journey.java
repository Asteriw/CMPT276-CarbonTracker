package com.cmpt276.kenneyw.carbonfootprinttracker;

import java.util.Date;
/*
Stores a Journey, and calculates CO2 emitted via this Journey in constructor and in seperate function to be called
when editing a pre-existing Journey.
 */
public class Journey {

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

    public Journey(String routeName, int cityDistance, int highwayDistance,
                   String carName, String gasType, double mpgCity, double mpgHighway, double literEngine,
                   Date dateOfTravel){
        this.routeName=routeName;
        this.cityDistance = cityDistance;
        this.highwayDistance = highwayDistance;
        this.name=carName;
        this.gasType=gasType;
        this.mpgCity=mpgCity;
        this.mpgHighway=mpgHighway;
        this.literEngine=literEngine;
        this.dateOfTravel=dateOfTravel;
        Calculation c=new Calculation();
        switch(gasType) {
            case "Premium":
                this.totalEmissions += c.calculateCO2Diesel(mpgCity,literEngine,cityDistance);
                this.totalEmissions += c.calculateCO2Diesel(mpgHighway,literEngine,highwayDistance);
                break;
            case "Regular":
                this.totalEmissions += c.calculateCO2Gasoline(mpgCity,literEngine,cityDistance);
                this.totalEmissions += c.calculateCO2Gasoline(mpgHighway,literEngine,highwayDistance);
                break;
            default:
                this.totalEmissions=0;
                break;
        }
    }

    public double CalculateTotalEmissions(){
        Calculation c=new Calculation();
        double totalEmissions=0;
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


    public String toString() {
        return name+" - "+routeName+" - "+cityDistance+" City, "+highwayDistance+" Highway"+" Date: "+dateOfTravel;
    }

}
