package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LastMonthActivity extends AppCompatActivity {

    String date_in_str;
    int Year;
    int Month;
    int Day;
    String prev_date_in_str;
    int prev_Year;
    int prev_Month;
    int prev_Day;

    JourneyCollection journeys = new JourneyCollection();
    UtilitiesCollection utilities = new UtilitiesCollection();

    private static final String TAG = "CarbonFootprintTracker";
    private static final String SHAREDPREF_SET_UTIL = "CarbonFootprintTrackerUtilities";
    private static final String SHAREDPREF_ITEM_AMOUNTOFUTILITIES = "AmountOfUtilities";
    private static final String UTILNAME ="name";
    private static final String GASTYPE="gasType";
    private static final String AMOUNT="amount";
    private static final String NUMPEOPLE="numofPeople";
    private static final String STARTDATE="startDate";
    private static final String ENDDATE="endDate";
    private static final String EMISSION = "emission";

    private static final String SHAREDPREF_SET_JOURNEY = "CarbonFootprintTrackerJournies";
    private static final String SHAREDPREF_ITEM_AMOUNTOFJOURNEYS = "AmountOfJourneys";
    public static final String NAME = "name";
    public static final String ROUTENAME = "routeName";
    public static final String CITY = "city";
    public static final String HIGHWAY = "highway";
    public static final String GASTYPE_JOURNEY="gasType";
    public static final String MPGCITY="mpgCity";
    public static final String MPGHIGHWAY="mpgHighway";
    public static final String DATESTRING="dateString";
    public static final String LITERENGINE="literEngine";
    public static final String TOTALEMISSIONS ="totalEmissions";
    public static final String BUS = "bus";
    public static final String BIKE = "bike";
    public static final String SKYTRAIN = "skytrain";

    PieChart chart;
    List<PieEntry> entries;
    PieDataSet dataSet;
    PieData data;

    int utilityAmt;
    int journeyAmt;
    int entriesSize=0;
    long amtDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_month);
        utilities=loadUtilities();
        journeys=loadJourneys();
        whatDayIsIt();
        whatDayIsThirtyDaysPrevious();
        setUpArrays();
        setUpPieChart();
    }

    private void setUpArrays() {
        entries = new ArrayList<>();

        while(!date_in_str.equals(prev_date_in_str)){
            for (int i = 0; i < journeyAmt; i++) {
                if(journeys.getJourney(i).getDateString().equals(date_in_str)){
                    entriesSize++;
                    entries.add(new PieEntry((float) journeys.getJourney(i).getTotalEmissions(),
                            journeys.getJourney(i).getName()));
                }
            }
            Day-=1;
            Date date=new Date(Year,Month,Day);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
            date_in_str=df.format(date);
            Log.i(TAG,"Prev date is : "+date_in_str);
        }
        whatDayIsIt();
        for(int i=0;i<utilityAmt;i++){
            if(
                    isBetween(date_in_str,utilities.getUtility(i).getStartDate(),utilities.getUtility(i).getEndDate())
                    ||
                    isBetween(prev_date_in_str,utilities.getUtility(i).getStartDate(),utilities.getUtility(i).getEndDate())){
                entriesSize++;


                if(isBefore(utilities.getUtility(i).getEndDate(),date_in_str)){
                    String[] first = prev_date_in_str.split("/");
                    String[] last = utilities.getUtility(i).getStartDate().split("/");
                    long numDays=countDays(first,last);
                    entries.add(new PieEntry((float)
                            utilities.getUtility(i).getEmission() / utilities.getUtility(i).getNumofPeople() / numDays,
                            utilities.getUtility(i).getName()));
                }
                else if(isBefore(prev_date_in_str,utilities.getUtility(i).getStartDate())){
                    String[] first = utilities.getUtility(i).getEndDate().split("/");
                    String[] last = date_in_str.split("/");
                    long numDays=countDays(first,last);
                    entries.add(new PieEntry((float)
                            utilities.getUtility(i).getEmission() / utilities.getUtility(i).getNumofPeople() / numDays,
                            utilities.getUtility(i).getName()));
                }
                else {
                    String[] first = utilities.getUtility(i).getStartDate().split("/");
                    String[] last = utilities.getUtility(i).getEndDate().split("/");
                    long numDays=countDays(first,last);
                    entries.add(new PieEntry((float)
                            utilities.getUtility(i).getEmission() / utilities.getUtility(i).getNumofPeople() / numDays,
                            utilities.getUtility(i).getName()));
                }
            }
        }
        /*
        String[] first = prev_date_in_str.split("/");
        String[] last = date_in_str.split("/");
        amtDays=countDays(first,last);
        for(int i=0;i<journeyAmt;i++){
            if(isBetween(journeys.getJourney(i).getDateString(),prev_date_in_str,date_in_str)){
                entriesSize++;
                entries.add(new PieEntry((float) journeys.getJourney(i).getTotalEmissions(),
                        journeys.getJourney(i).getName()));
            }
        }
        for(int i=0;i<utilityAmt;i++){
            if(
                    isBetween(utilities.getUtility(i).getStartDate(),prev_date_in_str,date_in_str)
                    ||
                    isBetween(utilities.getUtility(i).getEndDate(),prev_date_in_str,date_in_str)) {

                String[] firstone = utilities.getUtility(i).getStartDate().split("/");
                String[] lastone = utilities.getUtility(i).getEndDate().split("/");
                long numDays=countDays(firstone,lastone);
                entries.add(new PieEntry((float)
                        utilities.getUtility(i).getEmission()/utilities.getUtility(i).getNumofPeople()/numDays,
                        utilities.getUtility(i).getName()));
            }
        }*/
    }

    private void setUpPieChart() {
        dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueLineColor(Color.TRANSPARENT);
        dataSet.setSliceSpace(5.0f);
        dataSet.setValueTextSize(15);
        // set the data
        data = new PieData(dataSet);
        data.setValueTextSize(20);
        data.setValueTextColor(Color.BLACK);
        // Chart config
        chart = (PieChart) findViewById(R.id.chart2);
        chart.setData(data);
        chart.animateY(2000);
        chart.setCenterText("SUMMARY of\nCO2 EMISSION \nFOR LAST 28 DAYS");
        chart.setCenterTextSize(20);
        chart.setCenterTextColor(Color.DKGRAY);
        chart.setDescription(null);
        chart.getLegend().setEnabled(true);
        chart.setVisibility(View.VISIBLE);
        chart.invalidate();
    }

    private void whatDayIsIt() {
        Date date=new Date();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
        date_in_str=df.format(date);
        Log.i(TAG,"date is: "+date_in_str);

        String[] checkdate = date_in_str.split("/");
        Month=Integer.parseInt(checkdate[0]);
        Day=Integer.parseInt(checkdate[1]);
        Year=Integer.parseInt(checkdate[2]);
    }

    private void whatDayIsThirtyDaysPrevious(){

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
        Date date=new Date();
        date.setMonth( date.getMonth() - 1 );
        prev_date_in_str=df.format(date);
        Log.i(TAG,"date is: "+prev_date_in_str);

        String[] prevcheckdate = prev_date_in_str.split("/");
        prev_Month=Integer.parseInt(prevcheckdate[0]);
        prev_Day=Integer.parseInt(prevcheckdate[1]);
        prev_Year=Integer.parseInt(prevcheckdate[2]);
    }

    private long countDays(String[] first, String[] last) {
        Date dateOne=new Date(Integer.parseInt(first[1]), Integer.parseInt(first[2]),Integer.parseInt(first[0]));
        Date dateTwo=new Date(Integer.parseInt(last[1]), Integer.parseInt(last[2]),Integer.parseInt(last[0]));
        long timeOne = dateOne.getTime();
        long timeTwo = dateTwo.getTime();
        long oneDay = 1000 * 60 * 60 * 24;
        long delta = (timeTwo - timeOne) / oneDay;
        Log.i(TAG,"delta between 2 dates is: "+delta);
        return delta;
    }

    private boolean isBetween(String date,String firstDate, String lastDate){
        String[] checkdate = date.split("/");
        String[] first = firstDate.split("/");
        String[] last = lastDate.split("/");
        Log.i(TAG,"Checked date: "+date);
        return (
                //year
                Integer.parseInt(checkdate[2]) <= Integer.parseInt(last[2])
                        && Integer.parseInt(checkdate[2]) >= Integer.parseInt(first[2])
                        //month
                        && Integer.parseInt(checkdate[0]) <= Integer.parseInt(last[0])
                        && Integer.parseInt(checkdate[0]) >= Integer.parseInt(first[0])
                        //day
                        && Integer.parseInt(checkdate[1]) <= Integer.parseInt(last[1])
                        && Integer.parseInt(checkdate[1]) >= Integer.parseInt(first[1])
        );

    }

    private boolean isBefore(String firstDate, String lastDate){
        String[] last = lastDate.split("/");
        String[] first = firstDate.split("/");
        Date d1=new Date(Integer.parseInt(last[2]),Integer.parseInt(last[0]),Integer.parseInt(last[1]));
        Date d2=new Date(Integer.parseInt(first[2]),Integer.parseInt(first[0]),Integer.parseInt(first[1]));
        if(d1.before(d2)) {
            return true;
        }
        return false;
    }

    private UtilitiesCollection loadUtilities() {
        UtilitiesCollection utils = new UtilitiesCollection();
        SharedPreferences pref = getSharedPreferences(SHAREDPREF_SET_UTIL, MODE_PRIVATE);
        utilityAmt = pref.getInt(SHAREDPREF_ITEM_AMOUNTOFUTILITIES, 0);
        for (int i = 0; i < utilityAmt; i++) {
            Utility newUtil = new Utility(pref.getString(i + UTILNAME, ""),
                    pref.getString(i + GASTYPE, ""), Double.longBitsToDouble(pref.getLong(i + AMOUNT, 0)), pref.getInt(i + NUMPEOPLE, 0),
                    Double.longBitsToDouble(pref.getLong(i + EMISSION, 0)), pref.getString(i + STARTDATE, ""), pref.getString(i + ENDDATE, ""));
            utils.addUtility(newUtil);
        }
        return utils;
    }

    public JourneyCollection loadJourneys() {
        JourneyCollection temp_journeys =new JourneyCollection();
        SharedPreferences pref=getSharedPreferences(SHAREDPREF_SET_JOURNEY,MODE_PRIVATE);
        journeyAmt=pref.getInt(SHAREDPREF_ITEM_AMOUNTOFJOURNEYS,0);
        for(int i=0;i<journeyAmt;i++){
            //Date date=new Date(pref.getLong(i+DATEOFTRAVEL,0));
            Journey j=new Journey(
                    pref.getString(i+ROUTENAME,""),
                    Double.longBitsToDouble(pref.getLong(i+CITY,0)),
                    Double.longBitsToDouble(pref.getLong(i+HIGHWAY,0)),
                    pref.getString(i+NAME,""),
                    pref.getString(i+GASTYPE_JOURNEY,""),
                    Double.longBitsToDouble(pref.getLong(i+MPGCITY,0)),
                    Double.longBitsToDouble(pref.getLong(i+MPGHIGHWAY,0)),
                    Double.longBitsToDouble(pref.getLong(i+LITERENGINE,0)),
                    pref.getString(i+DATESTRING,""),
                    pref.getBoolean(i+BUS,false),
                    pref.getBoolean(i+BIKE,false),
                    pref.getBoolean(i+SKYTRAIN,false));
            temp_journeys.addJourney(j);
        }
        return temp_journeys;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, LastMonthActivity.class);
    }
}
