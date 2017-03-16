package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
    final static int col_size = 5;
    final static int row_size = 8; // must be consistent over every variable used for a pie chart/ table
    final static String column_1_header = "Date of Trip";
    final static String column_2_header = "Route";
    final static String column_3_header = "Distance (Km)";
    final static String column_4_header = "Vehicle";
    final static String column_5_header = "CO2 emitted (kg/L)";

    // Columns: date of trip, route name, distancee, vehicle name, CO2 emitted
    // the size of arrays must be constant
    float distance[]={100f,200f,300f,400f,500f,600f,700f,800f};
    String vehicleNames[] = {"Honda", "Ford", "GMC", "Subaru", "BMW", "Mercedes", "Porche", "Toyota"};
    String dateofTrips[] = {"January", "February", "March", "April", "May","June", "July", "August"};
    String routeNames[] = {"Home", "School", "Friend's house", "Park", "Recreation Centre", "Church", "Shop" ,"Restaurant"};
    double CO2emitted[] = {12.45, 53.27, 75.75, 34.58, 69.32, 68.56, 89.12, 56.45};

    // Chart Variables
    PieChart chart;
    List<PieEntry> entries;
    PieDataSet dataSet;
    PieData data;

    // Table Variables
    TableLayout journeyTable;
    TableLayout columnTable;
    TableRow journeytablerow;
    TableRow columntablerow;
    TextView col_1_content;
    TextView col_2_content;
    TextView col_3_content;
    TextView col_4_content;
    TextView col_5_content;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_foot_print);
        setupSwtichViewButton();
        setupBackButton();
        updateChart();
        updateTable();
    }

    //Populate a list of Pie entries
    private void updateChart() {
        // Create a Dataset
        entries = new ArrayList<>();

        for (int i = 0; i < distance.length; i++){
            //entries.add( new PieEntry(distance[i], vehicleNames[i]));
            //entries.add( new PieEntry(distance[i], dateofTrips[i]));
            //entries.add( new PieEntry(distance[i], routeNames[i]));
            entries.add( new PieEntry(distance[i], i));
        }

        // Config for each region of the chart
        dataSet = new PieDataSet(entries,"Fuel Consumption Rates of Car Brands");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.addColor(Color.MAGENTA);
        dataSet.addColor(Color.RED);
        dataSet.addColor(Color.CYAN);
        dataSet.setValueLineColor(Color.BLACK);

        // set the data
        data = new PieData(dataSet);
        data.setValueTextSize(20);

        // Chart config
        chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.animateY(2000);
        chart.setCenterText("SUMMARY\nof\nDATA");
        chart.setCenterTextSize(12);
        chart.setCenterTextColor(Color.DKGRAY);
        chart.setCenterTextOffset(0,5);
        chart.setDescription(null);
        chart.getLegend().setEnabled(false);
        chart.setVisibility(View.INVISIBLE);
        chart.invalidate();
    }

    // turn view of data; table to pie chart and vice versa
    private void setupSwtichViewButton() {
        Button swtichView_button = (Button) findViewById(R.id.switchview_button_carbon_foot_print);
        swtichView_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chart.getVisibility() == View.VISIBLE && journeyTable.getVisibility() == View.INVISIBLE) {
                    chart.setVisibility(View.INVISIBLE);
                    journeyTable.setVisibility(View.VISIBLE);
                }
                else{
                    chart.setVisibility(View.VISIBLE);
                    journeyTable.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    // Columns: date of trip, route name, distancee, vehicle name, CO2 emitted
    // Each column can be edited uniquely
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void updateTable() {
        setupColumnHeader();

        // Go through a list of journeys
        for (int row = 0; row < row_size; row++){
            journeytablerow = new TableRow(this);
            journeyTable.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f ));
            journeyTable.addView(journeytablerow);

            // Set content of each column
            col_1_content = new TextView(this);
            col_2_content = new TextView(this);
            col_3_content = new TextView(this);
            col_4_content = new TextView(this);
            col_5_content = new TextView(this);

            col_1_content.setText(dateofTrips[row]);
            col_2_content.setText(routeNames[row]);
            col_3_content.setText("" + distance[row]);
            col_4_content.setText(vehicleNames[row]);
            col_5_content.setText("" + CO2emitted[row]);

            col_1_content.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
            col_2_content.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
            col_3_content.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
            col_4_content.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
            col_5_content.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

            // Attributes //
            col_1_content.setWidth(7);
            col_2_content.setWidth(7);
            col_3_content.setWidth(7);
            col_4_content.setWidth(7);
            col_5_content.setWidth(7);

            col_1_content.setPadding(5,0,5,0);
            col_2_content.setPadding(5,0,5,0);
            col_3_content.setPadding(5,0,5,0);
            col_4_content.setPadding(5,0,5,0);
            col_5_content.setPadding(5,0,5,0);

            col_1_content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            col_2_content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            col_3_content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            col_4_content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            col_5_content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            journeytablerow.addView(col_1_content);
            journeytablerow.addView(col_2_content);
            journeytablerow.addView(col_3_content);
            journeytablerow.addView(col_4_content);
            journeytablerow.addView(col_5_content);
        }
        journeyTable.setVisibility(View.VISIBLE);
    }

    // This function add column headers in the first row of JourneyTable
    // Each column header can be edited uniquely
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setupColumnHeader(){
        journeyTable = (TableLayout) findViewById(R.id.journey_table);
        journeytablerow = new TableRow(this);
        journeytablerow.setBackgroundColor(Color.YELLOW);
        journeyTable.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f ));
        journeyTable.addView(journeytablerow);

        // Set content of each column
        col_1_content = new TextView(this);
        col_2_content = new TextView(this);
        col_3_content = new TextView(this);
        col_4_content = new TextView(this);
        col_5_content = new TextView(this);

        col_1_content.setText(column_1_header);
        col_2_content.setText(column_2_header);
        col_3_content.setText(column_3_header);
        col_4_content.setText(column_4_header);
        col_5_content.setText(column_5_header);

        col_1_content.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
        col_2_content.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
        col_3_content.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
        col_4_content.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));
        col_5_content.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0f));

        // Attributes //
        col_1_content.setWidth(7);
        col_2_content.setWidth(7);
        col_3_content.setWidth(7);
        col_4_content.setWidth(7);
        col_5_content.setWidth(7);

        col_1_content.setPadding(5,0,5,0);
        col_2_content.setPadding(5,0,5,0);
        col_3_content.setPadding(5,0,5,0);
        col_4_content.setPadding(5,0,5,0);
        col_5_content.setPadding(5,0,5,0);

        col_1_content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        col_2_content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        col_3_content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        col_4_content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        col_5_content.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

        journeytablerow.addView(col_1_content);
        journeytablerow.addView(col_2_content);
        journeytablerow.addView(col_3_content);
        journeytablerow.addView(col_4_content);
        journeytablerow.addView(col_5_content);
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