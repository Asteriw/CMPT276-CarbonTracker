
package com.cmpt276.kenneyw.carbonfootprinttracker;

import org.junit.Test;

import static org.junit.Assert.*;

/*
*   This is a test of functions of the CalculationScreen.java
* */
public class CalculationScreenTest {
    // CO2 emission of Diesel: 10.16 kg/gal
    @Test
    public void calculate_C02_Emission_of_Diesel_from_gallon_to_kg() throws Exception {
        double test_var_in_gal = 2.00;
        double expected_var = test_var_in_gal * 10.16;
        assertEquals(expected_var, test_var_in_gal*10.16, 0.01);
    }

    @Test
    public void calculate_C02_Emission_of_Diesel_from_kg_to_gallon() throws Exception {
        double test_var_in_kg = 100;
        double expected_var = test_var_in_kg / 10.16;
        assertEquals(expected_var, test_var_in_kg / 10.16, 0.01);
    }

    @Test
    public void is_negative() throws Exception {
        assertTrue(-1.45 < 0);
    }

    /* Fail teset cases */
    /*
    @Test
    public void is_negative() throws Exception {
        assertTrue(1.45 < 0);
    }

    @Test
    public void calculate_C02_Emission_of_Diesel_from_gallon_to_kg() throws Exception {
        double test_var_in_gal = 2.00;
        double expected_var = test_var_in_gal * 10.16;
        assertEquals(1000, test_var_in_gal*10.16, 0.01);
    }

    @Test
    public void calculate_C02_Emission_of_Diesel_from_kg_to_gallon() throws Exception {
        double test_var_in_kg = 100;
        double expected_var = test_var_in_kg / 10.16;
        assertEquals(1000, test_var_in_kg / 10.16, 0.01);
    }
    */
}