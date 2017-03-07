package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {
    // Chart Variables
    float fuelConsumptions[]={100,200,300,400,500,600,700,800};
    String carBrands[]={"Honda", "Ford", "GMC", "Subaru", "BMW", "Mercedes", "Porche", "Toyota"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        setupButtons();
        setupChart();
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

        // Get the chart;
        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.animateY(2000);
        chart.setCenterText("SUMMARY\nof\nDATA");
        chart.setCenterTextSize(20);
        chart.setCenterTextColor(Color.DKGRAY);
        chart.setCenterTextOffset(0,5);
        chart.invalidate();
    }

    private void setupButtons() {
        Button startCreate = (Button) findViewById(R.id.btncreatenew);
        startCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newJourneyIntent = SelectTransportation.makeIntent(MainMenu.this);
                startActivity(newJourneyIntent);
            }
        });
        Button startSettings = (Button) findViewById(R.id.btnviewtracked);
        startSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ViewTrackedIntent = ViewTracked.makeIntent(MainMenu.this);
                startActivity(ViewTrackedIntent);
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MainMenu.class);
    }
}
