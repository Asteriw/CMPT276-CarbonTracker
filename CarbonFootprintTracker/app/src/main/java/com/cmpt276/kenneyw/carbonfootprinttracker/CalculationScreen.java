package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/*
* CLASS DESCRIPTION:
*  Compute CO2 generated on a journey by how much gas was used during the trip for the city and highway portion.
*  Use the city08 and highway08 for miles per gallon of fuel. Only work with the primary fuel (ignore secondary fuels).
*
* REFERENCE:
*  CO2 emitted by fuel (https://www.eia.gov/environment/emissions/co2_vol_mass.cfm):
*  Gasoline (all grades): 8.89 Kilograms of CO2 per gallon [done]
*  Diesel:      10.16 Kilograms  of CO2 per gallon [done]
*  Electric:    0 (our electricity is mainly hydro) [done]
*  Natural Gas: Ignore (don't even list these in the app) [done]
*
* OPTION:
*  User interface must be in metric: Litres, Kilometers, and kilograms or metric tons (whichever you think best).
*
* CONSTRAINT:
*  Negative values are not allowed.
* */

public class CalculationScreen extends AppCompatActivity {
    private static final String SHAREDPREF_SET = "CarbonFootprintTracker";
    private static final String SHAREDPREF_ITEM_CARNAME = "CarName";
    private static final String SHAREDPREF_ITEM_MAKE = "CarMake";
    private static final String SHAREDPREF_ITEM_MODEL = "CarModel";
    private static final String SHAREDPREF_ITEM_CITYMPG = "CityMPG";
    private static final String SHAREDPREF_ITEM_HIGHWAYMPG = "HighwayMPG";
    private static final String SHAREDPREF_ITEM_ROUTENAME = "RouteName";
    private static final String SHAREDPREF_ITEM_AMOUNTOFROUTES = "AmountOfRoutes";
    private static final String SHAREDPREF_ITEM_AMOUNTOFTRIPS = "AmountOfTrips";
    private static final String SHAREDPREF_ITEM_CO2EMISSIONS = "CO2Emissions";
    private static final String SHAREDPREF_ITEM_AMOUNTOFCARS = "AmountOfCars";
    private static final String SHAREDPREF_ITEM_CITYDISTANCE = "CityDistance";
    private static final String SHAREDPREF_ITEM_HIGHWAYDISTANCE = "HighwayDistance";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_screen);
        TextView calculationResult = (TextView) findViewById(R.id.calculation_result);
        calculationResult.setText("Temporary");
    }

    private static DecimalFormat df2 = new DecimalFormat(".##"); // for decimal precision
    final private static double gasoline_CO2_emission_kg_per_gal = 8.89;
    final private static double diesel_CO2_emission_kg_per_gal = 10.16;

    /*Convert CO2 Emission from gallons to kilograms*/
    public double calculate_C02_Emission_of_Diesel_from_gallon_to_kg(double var_in_gal){
        double result_in_kg;
        result_in_kg = var_in_gal * diesel_CO2_emission_kg_per_gal;
        df2.setRoundingMode(RoundingMode.UP); // round a double to 2 decimaal places
        df2.format(result_in_kg);
        return result_in_kg; // return with 2 decimal places
    }

    public double calculate_CO2_Emission_of_Gasolline_from_gallon_to_kg(double var_in_gal){
        double result_in_kg;
        result_in_kg = var_in_gal * gasoline_CO2_emission_kg_per_gal;
        df2.setRoundingMode(RoundingMode.UP); // round a double to 2 decimaal places
        df2.format(result_in_kg);
        return result_in_kg; // return with 2 decimal places
    }

    /*Convert CO2 Emission from kilograms to gallons*/
    public double calculate_C02_Emission_of_Diesel_from_kg_to_gallon(double var_in_kg){
        double result_in_gal;
        result_in_gal = var_in_kg / diesel_CO2_emission_kg_per_gal;
        df2.setRoundingMode(RoundingMode.UP); // round a double to 2 decimaal places
        df2.format(result_in_gal);
        return result_in_gal; // return with 2 decimal places
    }

    public double calculate_CO2_Emission_of_Gasolline_from_kg_to_gallon(double var_in_kg){
        double result_in_gal;
        result_in_gal = var_in_kg / gasoline_CO2_emission_kg_per_gal;
        df2.setRoundingMode(RoundingMode.UP); // round a double to 2 decimaal places
        df2.format(result_in_gal);
        return result_in_gal; // return with 2 decimal places
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, CalculationScreen.class);
    }

}
