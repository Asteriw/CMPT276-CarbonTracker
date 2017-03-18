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
    private static int row_size; // must be consistent over every variable used for a pie chart/ table
    final static String column_1_header = "Date of Trip";
    final static String column_2_header = "Route";
    final static String column_3_header = "Distance (Km)";
    final static String column_4_header = "Vehicle";
    final static String column_5_header = "CO2 emitted (kg/L)";
    ArrayList<Journey> journeyArrayList=new ArrayList<>();


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



    // Columns: date of trip, route name, distancee, vehicle name, CO2 emitted
    // the size of arrays must be constant
    /*
    float distance[]={100f,200f,300f,400f,500f,600f,700f,800f};
    String vehicleNames[] = {"Honda", "Ford", "GMC", "Subaru", "BMW", "Mercedes", "Porche", "Toyota"};
    String dateofTrips[] = {"January", "February", "March", "April", "May","June", "July", "August"};
    String routeNames[] = {"Home", "School", "Friend's house", "Park", "Recreation Centre", "Church", "Shop" ,"Restaurant"};
    float CO2emitted[] = {12.45f, 53.27f, 75.75f, 34.58f, 69.32f, 68.56f, 89.12f, 56.45f};
   */

    String vehicleNames[];
    String dateofTrips[];
    String routeNames[];
    float CO2emitted[];
    float distance[];

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

        setUpArrays();

        setupBackButton();
        updateChart();
        updateTable();
    }

    private void setUpArrays() {
        journeyArrayList=loadJourneys();
        int size=journeyArrayList.size();
        row_size=size;
        vehicleNames=new String[size];
        routeNames=new String[size];
        CO2emitted=new float[size];
        dateofTrips=new String[size];
        distance=new float[size];

        for(int i=0;i<journeyArrayList.size();i++){
            Journey journey = journeyArrayList.get(i);

            vehicleNames[i]= journey.getName();
            routeNames[i]= journey.getRouteName();
            CO2emitted[i]=(float) journey.getTotalEmissions();
            dateofTrips[i]= journey.getDateOfTravel().toString();
            distance[i]= journey.getHighwayDistance()+ journey.getCityDistance();
        }
    }

    public ArrayList<Journey> loadJourneys() {
        ArrayList<Journey> journeyArrayList=new ArrayList<>();
        SharedPreferences pref=getSharedPreferences(SHAREDPREF_SET,MODE_PRIVATE);
       /* String routeName;int cityDistance;int highwayDistance;
        String name;String gasType;double mpgCity;double mpgHighway;
        String transmission;double literEngine;Date dateOfTravel;double totalEmissions;
       */

        int journeyAmt=pref.getInt(SHAREDPREF_ITEM_AMOUNTOFJOURNEYS,0);
        for(int i=0;i<journeyAmt;i++){
            Date d=new Date(pref.getLong(i+DATEOFTRAVEL,0));
            Journey j=new Journey(pref.getString(i+ROUTENAME,""),pref.getInt(i+CITY,0),pref.getInt(i+HIGHWAY,0),
                    pref.getString(i+NAME,""),pref.getString(i+GASTYPE,""),Double.longBitsToDouble(pref.getLong(i+MPGCITY,0)),
                    Double.longBitsToDouble(pref.getLong(i+MPGHIGHWAY,0)),Double.longBitsToDouble(pref.getLong(i+LITERENGINE,0)),
                    d,pref.getBoolean(i+"bus",false),pref.getBoolean("bike",false),pref.getBoolean("skytrain",false));
            journeyArrayList.add(j);
        }

        return journeyArrayList;
    }
    //Populate a list of Pie entries
    private void updateChart() {
        // Create a Dataset
        entries = new ArrayList<>();

        for (int i = 0; i < distance.length; i++){
            //entries.add( new PieEntry(distance[i], vehicleNames[i]));
            //entries.add( new PieEntry(distance[i], dateofTrips[i]));
            //entries.add( new PieEntry(distance[i], routeNames[i]));
            entries.add( new PieEntry(CO2emitted[i], dateofTrips[i]));
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