package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.support.v7.app.AppCompatActivity;

import java.util.Date;

public class Utility extends AppCompatActivity {
    private String name;
    private String gasType;
    private double emissions;
    private double kiloWattHour;
    private Date startDate;
    private Date endDate;

    private enum gasTypeEnum {
        NATURAL_GAS,
        ELECTRIC
    }

    public Utility() {
        name = "";
        gasType = gasTypeEnum.NATURAL_GAS.toString();
        emissions = 0.0;
        kiloWattHour = 0.0;
        startDate = new Date();
        endDate = new Date();
    }

    public Utility(String name, String gasType, double emissions, double kiloWattHour, Date startDate, Date endDate) {
        this.name = name;
        if (gasType.matches("Electric")) {
            this.gasType = gasTypeEnum.ELECTRIC.toString();
        } else {
            this.gasType = gasTypeEnum.NATURAL_GAS.toString();
        }
        this.emissions = emissions;
        this.kiloWattHour = kiloWattHour;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGasType() {
        return gasType;
    }

    public void setGasType(String gasType) {
        this.gasType = gasType;
    }

    public double getEmissions() {
        return emissions;
    }

    public void setEmissions(double emissions) {
        this.emissions = emissions;
    }

    public double getKiloWattHour() {
        return kiloWattHour;
    }

    public void setKiloWattHour(double kiloWattHour) {
        this.kiloWattHour = kiloWattHour;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date date) {
        this.endDate = date;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }
}