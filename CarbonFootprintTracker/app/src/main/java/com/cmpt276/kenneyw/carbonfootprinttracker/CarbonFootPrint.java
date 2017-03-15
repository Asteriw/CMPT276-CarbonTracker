package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/*
*   This class displays a table or a pie chart of journeys user created.
*   User is allowed to switch view
*
*   all known trips must be shown
*
* Requirements:
- User has app open and has entered in one or more journeys
- From the main menu, user selects to view current carbon footprint.
- User is shown a table of car trips. Columns include date of trip, route name (if any), distance, vehicle name, and carbon emitted.
- User is shown a graph of the car trips (or user is able to switch from the table to the graph and back as desired)
	Graph may be either a stacked bar graph, or a pie graph.
	All known trips are shown in the graph (not restricted to a single day).
*
 * [20] Display Carbon Footprint
    - Deleting a route or car does *not* affect the details shown here.
    - Editing a route or a car *does* affect the details shown here.
   [10] View table of journeys: date of trip, route name, distance, vehicle name, carbon emitted.
   [10] Able to switch to a graph view (either stacked bar or pie).
 *
 *
* */

public class CarbonFootPrint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_foot_print);
        setupSwtichViewButton();
        setupBackButton();
        setupChart();
    }

    private void setupChart() {
        // Chart Variables
        float fuelConsumptions[]={100,200,300,400,500,600,700,800};
        String carBrands[]={"Honda", "Ford", "GMC", "Subaru", "BMW", "Mercedes", "Porche", "Toyota"};

        //Populate a list of Pie entries
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < fuelConsumptions.length; i++){
            pieEntries.add( new PieEntry(fuelConsumptions[i], carBrands[i]) );
        }
        // Config for each region of the chart
        PieDataSet dataSet = new PieDataSet(pieEntries,"Fuel Consumption Rates of Car Brands");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.addColor(Color.MAGENTA);
        dataSet.addColor(Color.RED);
        dataSet.addColor(Color.CYAN);
        dataSet.setValueLineColor(Color.BLACK);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(20);

        // Get the chart;
        PieChart chart = (PieChart) findViewById(R.id.piechart);

        // Chart config
        chart.setData(data);
        chart.animateY(2000);
        chart.setCenterText("SUMMARY\nof\nDATA");
        chart.setCenterTextSize(20);
        chart.setCenterTextColor(Color.DKGRAY);
        chart.setCenterTextOffset(0,5);
        chart.setVisibility(View.VISIBLE);
        chart.setDescription(null);
        chart.invalidate();

    }


    private void setupSwtichViewButton() {
        Button swtichView_button = (Button) findViewById(R.id.switchview_button_carbon_foot_print);
        swtichView_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // turn view of data; table to pie chart and vice versa

            }
        });
    }

    // Columns: date of trip, route name, distancee, vehicle name, CO2 emitted
    private void displayTable() {
        // Go through a list of journeys

        // add a new row

        // display
    }







    private void setupBackButton() {
        Button back_button = (Button) findViewById(R.id.back_button_carbon_foot_print);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, CarbonFootPrint.class);
    }
}