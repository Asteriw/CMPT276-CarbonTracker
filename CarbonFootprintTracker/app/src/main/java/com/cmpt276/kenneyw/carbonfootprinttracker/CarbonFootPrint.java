package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import java.util.Date;
import java.util.List;

/*
*   This class displays a table or a pie chart of journeys user created.
*   User is allowed to switch view
*
* */

public class CarbonFootPrint extends AppCompatActivity {
    private static int row_size; // must be consistent over every variable used for a pie chart/ table
    final static String column_1_header = "Date of Trip";
    final static String column_2_header = "Route";
    final static String column_3_header = "Distance (Km)";
    final static String column_4_header = "Vehicle";
    final static String column_5_header = "CO2 emitted (kg/L)";
    private static final String SHAREDPREF_SET = "CarbonFootprintTrackerJournies";
    private static final String SHAREDPREF_ITEM_AMOUNTOFJOURNEYS = "AmountOfJourneys";
    public static final String NAME = "name";
    public static final String ROUTENAME = "routeName";
    public static final String CITY = "city";
    public static final String HIGHWAY = "highway";
    public static final String GASTYPE="gasType";
    public static final String MPGCITY="mpgCity";
    public static final String MPGHIGHWAY="mpgHighway";
    public static final String LITERENGINE="literEngine";
    public static final String DATEOFTRAVEL="DateOfTravel";

    JourneyCollection journeys = new JourneyCollection();

    // Chart Variables
    PieChart chart;
    List<PieEntry> entries;
    PieDataSet dataSet;
    PieData data;

    // Table Variables
    TableLayout journeyTable;
    TableRow journeytablerow;
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

        loadJourneys(journeys);
        row_size = journeys.countJourneys();

        setupBackButton();
        updateChart();
        updateTable();
    }

    public JourneyCollection loadJourneys( JourneyCollection journeys) {
        SharedPreferences pref=getSharedPreferences(SHAREDPREF_SET,MODE_PRIVATE);
       /* String routeName;int cityDistance;int highwayDistance;
        String name;String gasType;double mpgCity;double mpgHighway;
        String transmission;double literEngine;Date dateOfTravel;double totalEmissions;
       */
        int num_of_journeys = pref.getInt(SHAREDPREF_ITEM_AMOUNTOFJOURNEYS,0);
        for(int i=0; i<num_of_journeys; i++){
            Date date = new Date(pref.getLong(i+DATEOFTRAVEL,0));
            Journey temp_journey = new Journey(pref.getString(i+ROUTENAME,""),
                    pref.getInt(i+CITY,0),pref.getInt(i+HIGHWAY,0),
                    pref.getString(i+NAME,""),
                    pref.getString(i+GASTYPE,""),
                    Double.longBitsToDouble(pref.getLong(i+MPGCITY,0)),
                    Double.longBitsToDouble(pref.getLong(i+MPGHIGHWAY,0)),
                    Double.longBitsToDouble(pref.getLong(i+LITERENGINE,0)),
                    date,pref.getBoolean(i+"bus",false),pref.getBoolean("bike",false),pref.getBoolean("skytrain",false));
            journeys.addJourney(temp_journey);
        }
        return journeys;
    }

    //Populate a list of Pie entries
    private void updateChart() {
        // Create a Dataset
        entries = new ArrayList<>();

        for (int i = 0; i < row_size; i++){
            entries.add( new PieEntry( (float) journeys.getJourney(i).getTotalEmissions(), journeys.getJourney(i).getName()) );
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
        chart.setCenterTextSize(20);
        chart.setCenterTextColor(Color.DKGRAY);
        //chart.setCenterTextOffset(0,5);
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

            col_1_content.setText( journeys.getJourney(row).getDateOfTravel().toString() );
            col_2_content.setText( journeys.getJourney(row).getRouteName() );
            col_3_content.setText("" + (float) journeys.getJourney(row).getCityDistance() + (float) journeys.getJourney(row).getHighwayDistance() );
            col_4_content.setText( journeys.getJourney(row).getName() );
            col_5_content.setText("" + (float) journeys.getJourney(row).getTotalEmissions());

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
        journeytablerow.setBackgroundColor(0xFF10ce20);
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

        col_1_content.setTextColor(Color.BLACK);
        col_2_content.setTextColor(Color.BLACK);
        col_3_content.setTextColor(Color.BLACK);
        col_4_content.setTextColor(Color.BLACK);
        col_5_content.setTextColor(Color.BLACK);

        col_1_content.setTypeface(Typeface.DEFAULT_BOLD);
        col_2_content.setTypeface(Typeface.DEFAULT_BOLD);
        col_3_content.setTypeface(Typeface.DEFAULT_BOLD);
        col_4_content.setTypeface(Typeface.DEFAULT_BOLD);
        col_5_content.setTypeface(Typeface.DEFAULT_BOLD);

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