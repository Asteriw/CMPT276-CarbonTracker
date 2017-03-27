package com.cmpt276.kenneyw.carbonfootprinttracker;
import android.support.v7.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
    Stores a Journey, and calculates CO2 emitted via this Journey in constructor and in separate function
    which is called when editing a pre-existing Journey.
     */
public class Journey extends AppCompatActivity {
    private String routeName;
    private double cityDistance;
    private double highwayDistance;
    private String name;
    private String gasType;
    private double mpgCity;
    private double mpgHighway;
    private double literEngine;
    private String dateString;
    private double totalEmissions;
    private boolean Bike;
    private boolean Bus;
    private boolean Skytrain;
    public static final double KWH_PER_KM = 6.848;
    public static final int CO2_PER_KWH = 9;
    public Journey(String routeName, double cityDistance, double highwayDistance,
                   String carName, String gasType, double mpgCity, double mpgHighway, double literEngine,
                   String dateString,boolean bus,boolean bike,boolean skytrain){
        this.routeName=routeName;
        this.cityDistance = cityDistance;
        this.highwayDistance = highwayDistance;
        this.name=carName;
        this.gasType=gasType;
        this.mpgCity=mpgCity;
        this.mpgHighway=mpgHighway;
        this.literEngine=literEngine;
        this.dateString=dateString;
        this.Bus=bus;
        this.Bike=bike;
        this.Skytrain=skytrain;
        this.totalEmissions=CalculateTotalEmissions();
    }
    public double CalculateTotalEmissions(){
        Calculation c=new Calculation();
        double totalEmissions=0;
        if(!this.Bus && !this.Bike && !this.Skytrain )
            switch(this.gasType) {
                case "Premium":
                    totalEmissions += c.calculateCO2Diesel(mpgCity,cityDistance);
                    totalEmissions += c.calculateCO2Diesel(mpgHighway,highwayDistance);
                    break;
                case "Regular":
                    totalEmissions += c.calculateCO2Gasoline(mpgCity,cityDistance);
                    totalEmissions += c.calculateCO2Gasoline(mpgHighway,highwayDistance);
                    break;
                default:
                    totalEmissions=0;
                    break;
            }
        //89 grams of CO2 per km
        if(this.Bus){
            totalEmissions=0.089*(this.cityDistance+this.highwayDistance);
        }
        //<--From: http://ctrf.ca/wp-content/uploads/2015/05/CTRF2015NguyenSangOramPerlTransportationEnvironment.pdf-->
        else if(this.Skytrain){
            totalEmissions= KWH_PER_KM * (this.cityDistance+this.highwayDistance) * CO2_PER_KWH; //divide by number of ppl
        }
        return doubleToTwoPlaces(totalEmissions);
    }
    private double doubleToTwoPlaces(double result_in_kg_CO2) {
        /*if(result_in_kg_CO2*8==0){return 0;}
        DecimalFormat df2 = new DecimalFormat("#0.00");
        result_in_kg_CO2= Double.parseDouble(df2.format(result_in_kg_CO2));
        return result_in_kg_CO2;
        result_in_kg_CO2 = result_in_kg_CO2 * 100;
        result_in_kg_CO2 = (double)Math.round(result_in_kg_CO2);
        result_in_kg_CO2 = result_in_kg_CO2/100;*/
        return result_in_kg_CO2;
    }
    public String getDateString() {return dateString;}
    public void setDateString(String dateString) {this.dateString = dateString;}
    public double getCityDistance() {
        return cityDistance;
    }
    public void setCityDistance(double cityDistance) {
        this.cityDistance = cityDistance;
    }
    public String getGasType() {
        return gasType;
    }
    public void setGasType(String gasType) {
        this.gasType = gasType;
    }
    public double getHighwayDistance() {
        return highwayDistance;
    }
    public void setHighwayDistance(double highwayDistance) {
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
        return
                name+
                        " - "
                        +routeName+
                        " - "
                        +cityDistance+ " km City, "
                        +highwayDistance+"km Highway"
                        + " - Date: "+dateString;
    }
}