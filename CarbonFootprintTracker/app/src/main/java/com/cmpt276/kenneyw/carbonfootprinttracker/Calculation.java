package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;

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
*  Formula:
*
*   ____ Km  *  ___ miles/km * ___ miles/gallon * ___ kg/gallon = ___ kg/L (Kg CO2 per litre)
* (from user)                      (from CSV)                     (Result)
*
*   User interface is metric: Litres and Kilometers
*   L is the engine displacement of a selected car, rerad from CSV.
*   city08 and higway08 are used for miles per gallon of fuel. Only focus on the primary fuel.
*
*
* CONSTRAINT:
*  Negative values are not allowed.
*
* */

public class Calculation {

    final private static double gasoline_volume_in_kg_per_gallon = 8.89;
    final private static double diesel_volumne_in_kg_per_gallon = 10.16;
    final private static double km_per_mile = 0.621371;

    /*Calculate CO2 Emission of Gasoline with a given distance*/
    private double calculate_CO2_Emission_of_Gasoline(double distance_in_km_from_user, double miles_per_gallon) {
        double result_in_kg_CO2_per_litre;
        result_in_kg_CO2_per_litre = (distance_in_km_from_user) *
                (1 / km_per_mile) *
                (miles_per_gallon) *
                (gasoline_volume_in_kg_per_gallon);
        return doubleToTwoPlaces(result_in_kg_CO2_per_litre);
    }


    /*Calculate CO2 Emission of Diesel with a given distance*/
    private double calculate_C02_Emission_of_Diesel_from_kg_to_gallon(double distance_in_km_from_user, double miles_per_gallon) {
        double result_in_kg_CO2_per_litre;
        result_in_kg_CO2_per_litre = (distance_in_km_from_user) *
                (1 / km_per_mile) *
                (miles_per_gallon) *
                (diesel_volumne_in_kg_per_gallon);
        return doubleToTwoPlaces(result_in_kg_CO2_per_litre);
    }

    private double doubleToTwoPlaces(double result_in_kg_CO2_per_litre) {
        DecimalFormat df2 = new DecimalFormat("#.##");
        result_in_kg_CO2_per_litre = Double.valueOf(df2.format(result_in_kg_CO2_per_litre));
        return result_in_kg_CO2_per_litre;
    }

    public double calculateCO2Diesel(double mpg, double distance) {
        return calculate_C02_Emission_of_Diesel_from_kg_to_gallon(mpg, distance);
    }

    public double calculateCO2Gasoline(double mpg, double distance) {
        return calculate_CO2_Emission_of_Gasoline(mpg, distance);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, Calculation.class);
    }
}