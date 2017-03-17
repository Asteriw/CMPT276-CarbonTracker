package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.support.v7.app.AppCompatActivity;

import java.util.Date;

public class JourneySingleton extends AppCompatActivity {
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

    private static JourneySingleton journeyInstance = null;

    private JourneySingleton(){
        this.routeName = "";
        this.cityDistance = 0;
        this.highwayDistance = 0;
        this.name = "";
        this.gasType = "";
        this.mpgCity = 0;
        this.mpgHighway = 0;
        this.literEngine = 0;
        this.dateOfTravel = null;
    }

    public static JourneySingleton getInstance(){
        if(journeyInstance == null){
            journeyInstance = new JourneySingleton();
        }
        return journeyInstance;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
}
