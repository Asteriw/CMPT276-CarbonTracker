package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ViewTracked extends AppCompatActivity {
    // Chart Variables
    float fuelConsumptions[]={100,200,300,400,500,600,700,800};
    String carBrands[]={"Honda", "Ford", "GMC", "Subaru", "BMW", "Mercedes", "Porche", "Toyota"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tracked);
        setupChart();
        setupFlipper();
    }

    private void setupFlipper() {
        final ViewFlipper viewflip = (ViewFlipper) findViewById(R.id.viewFlipper);
        Button flipbutton = (Button) findViewById(R.id.view_flip);
        flipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewflip.showNext();
            }
        });
    }

    private void setupChart() {
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

        // If clicked, turn a table view
        PieChart chart = (PieChart) findViewById(R.id.piechart);
        // Get the chart;
        chart.setData(data);
        chart.animateY(2000);
        chart.setCenterText("SUMMARY\nof\nDATA");
        chart.setCenterTextSize(20);
        chart.setCenterTextColor(Color.DKGRAY);
        chart.setCenterTextOffset(0,5);
        chart.setVisibility(View.VISIBLE);
        chart.setDescription(null);
        chart.invalidate();

        /*
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chart.setVisibility(View.INVISIBLE);
                setupTable();
            }
        });
        */

    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, ViewTracked.class);
    }
}
