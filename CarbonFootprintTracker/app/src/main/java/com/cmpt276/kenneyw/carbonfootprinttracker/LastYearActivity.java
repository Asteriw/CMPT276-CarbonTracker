package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LastYearActivity extends AppCompatActivity {

    public static final double PARISACCORDCO2PERCAPITA = 19.04;

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

    //for pie chart
    PieChart chart;
    PieDataSet dataSet;
    PieData data;
    int entriesSize=0;
    List<PieEntry> entriesForAllMonth =new ArrayList<>();
    ArrayList<Float> ems=new ArrayList<>();
    ArrayList<String> names=new ArrayList<>();

    //for bar chart
    ArrayList<String> months=new ArrayList<>();
    BarChart barChart;
    int entriesSizeMonthly=0;
    ArrayList<Float> emsMonthly=new ArrayList<>();
    ArrayList<String> namesMonthly=new ArrayList<>();
    ArrayList<Entry> averageCanadianAxis=new ArrayList<>();
    ArrayList<Entry> ParisAccordAxis=new ArrayList<>();

    int utilityAmt;
    int journeyAmt;
    double totalems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_year);

        utilities=loadUtilities();
        journeys=loadJourneys();
        whatDayIsIt();
        whatDayIsThirtyDaysPrevious();
        setUpArrays();
        setUpPieChart();
        setUpButtons();

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

    private void setUpArrays() {

        barChart = (BarChart) findViewById(R.id.barChart);
        BarData bd;
        ArrayList<BarDataSet> dataSets = null;
        entriesForAllMonth =new ArrayList<>();

        for(int counter=0;counter<12;counter++){
            //number of months to loop for

            entriesSizeMonthly=0;
            emsMonthly=new ArrayList<>();
            namesMonthly=new ArrayList<>();

            String[] firstjourn = prev_date_in_str.split("/");
            String[] lastjourn = date_in_str.split("/");
            long datesbetween=countDays(firstjourn,lastjourn);

            //array lists for names and emissions, same index has emission data for same car name
            //(transportation mode included)


            //loop for amount of journeys
            for (int i = 0; i < journeyAmt; i++) {
                //if date is in current month
                if (isBetween(journeys.getJourney(i).getDateString(), prev_date_in_str, date_in_str)) {
                    String nameOfJourney=journeys.getJourney(i).getName();
                    //if car is known in names arraylist
                    if(names.contains(nameOfJourney)){
                        //add emission data on that index
                        for(int j=0;j<entriesSize;j++) {
                            if(nameOfJourney.equals(names.get(j))) {
                                ems.add(j,(float)journeys.getJourney(i).getTotalEmissions());
                            }
                        }
                    }
                    else {
                        //else increment size, make new index to store that car
                        entriesSize++;
                        names.add(journeys.getJourney(i).getName());
                        //add emission data on that index
                                ems.add(entriesSize-1,(float)journeys.getJourney(i).getTotalEmissions());
                        }

                    if(namesMonthly.contains(nameOfJourney)){
                        for(int j=0;j<entriesSizeMonthly;j++) {
                            if(nameOfJourney.equals(namesMonthly.get(j))) {
                                emsMonthly.add(j,(float)journeys.getJourney(i).getTotalEmissions());
                            }
                        }
                    }
                    else{
                        entriesSizeMonthly++;
                        namesMonthly.add(journeys.getJourney(i).getName());
                        //add emission data on that index
                        emsMonthly.add(entriesSizeMonthly-1,(float)journeys.getJourney(i).getTotalEmissions());
                    }
                }
            }

            for(int i=0;i<utilityAmt;i++){

                if(     isBetween(date_in_str,utilities.getUtility(i).getStartDate(),utilities.getUtility(i).getEndDate())
                        ||
                        isBetween(prev_date_in_str,utilities.getUtility(i).getStartDate(),utilities.getUtility(i).getEndDate())){

                    if(isBefore(utilities.getUtility(i).getEndDate(),date_in_str)
                            && isBefore(utilities.getUtility(i).getStartDate(),prev_date_in_str)){

                        String[] firstU = utilities.getUtility(i).getStartDate().split("/");
                        String[] lastU = utilities.getUtility(i).getEndDate().split("/");
                        long numDaysForUtility=countDays(firstU,lastU);

                        String[] first = prev_date_in_str.split("/");
                        String[] last = utilities.getUtility(i).getEndDate().split("/");
                        long numDays=countDays(first,last);

                        entriesSize++;
                        names.add(utilities.getUtility(i).getName());
                        ems.add((float)utilities.getUtility(i).getEmission() /
                                utilities.getUtility(i).getNumofPeople() /
                                numDaysForUtility * numDays);

                        entriesSizeMonthly++;
                        namesMonthly.add(utilities.getUtility(i).getName());
                        emsMonthly.add((float)utilities.getUtility(i).getEmission() /
                                utilities.getUtility(i).getNumofPeople() /
                                numDaysForUtility * numDays);
                    }

                    else if(isBefore(prev_date_in_str,utilities.getUtility(i).getStartDate())
                            && isBefore(date_in_str,utilities.getUtility(i).getEndDate())){

                        String[] firstU = utilities.getUtility(i).getStartDate().split("/");
                        String[] lastU = utilities.getUtility(i).getEndDate().split("/");
                        long numDaysForUtility=countDays(firstU,lastU);

                        String[] first = utilities.getUtility(i).getStartDate().split("/");
                        String[] last = date_in_str.split("/");
                        long numDays=countDays(first,last);

                        entriesSize++;

                        names.add(utilities.getUtility(i).getName());
                        ems.add((float)utilities.getUtility(i).getEmission() /
                                utilities.getUtility(i).getNumofPeople() /
                                numDaysForUtility * numDays);

                        entriesSizeMonthly++;
                        namesMonthly.add(utilities.getUtility(i).getName());
                        emsMonthly.add((float)utilities.getUtility(i).getEmission() /
                                utilities.getUtility(i).getNumofPeople() /
                                numDaysForUtility * numDays);
                    }

                    else{
                        entriesSize++;
                        names.add(utilities.getUtility(i).getName());
                        ems.add((float)utilities.getUtility(i).getEmission() /
                        utilities.getUtility(i).getNumofPeople());

                        entriesSizeMonthly++;
                        namesMonthly.add(utilities.getUtility(i).getName());
                        emsMonthly.add((float)utilities.getUtility(i).getEmission() /
                                utilities.getUtility(i).getNumofPeople());
                    }
                }

                else{
                    String[] firstU = utilities.getUtility(i).getStartDate().split("/");
                    String[] lastU = utilities.getUtility(i).getEndDate().split("/");
                    long numDaysForUtility=countDays(firstU,lastU);
                    entriesSize++;
                    names.add(utilities.getUtility(i).getName());
                    ems.add((float)utilities.getUtility(i).getEmission() /
                            utilities.getUtility(i).getNumofPeople()/
                            numDaysForUtility * datesbetween);

                    entriesSizeMonthly++;
                    namesMonthly.add(utilities.getUtility(i).getName());
                    emsMonthly.add((float)utilities.getUtility(i).getEmission() /
                            utilities.getUtility(i).getNumofPeople()/
                            numDaysForUtility * datesbetween);
                }
            }
            //entriesForAllMonth.add(new PieEntry((float)totalems,"Month "+(12-counter+1)));

            date_in_str=prev_date_in_str;
            whatDayIsThirtyDaysPreviousOfThis(date_in_str);

            ArrayList<BarEntry> valueSet = new ArrayList<BarEntry>();
            for(int i=0;i<entriesSizeMonthly;i++) {
                BarEntry v1 = new BarEntry(counter,emsMonthly.get(i),namesMonthly.get(i));
                valueSet.add(v1);
            }
            BarDataSet bds=new BarDataSet(valueSet,"Month: "+counter);
            dataSets.add(counter,bds);
            bd=new BarData(bds);
            barChart.setData(bd);

        }
        //For PieChart: All Months, Segregated Data according to transportation mode / name, and Utility name
        for(int i=0;i<entriesSize;i++){
            totalems+=ems.get(i);
            entriesForAllMonth.add(i,new PieEntry(ems.get(i),names.get(i)));
        }
    }

    private void setUpPieChart() {
        dataSet = new PieDataSet(entriesForAllMonth, "");
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        dataSet.setValueLineColor(Color.TRANSPARENT);
        dataSet.setSliceSpace(1.0f);
        dataSet.setValueTextSize(15);
        // set the data
        data = new PieData(dataSet);
        data.setValueTextSize(20);
        data.setValueTextColor(Color.WHITE);
        // Chart config
        chart = (PieChart) findViewById(R.id.chart3);
        chart.setData(data);
        chart.animateY(2000);
        chart.setCenterText("SUMMARY of\nCO2 EMISSION\nFOR LAST 365 DAYS");
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
        String[] prevcheckdate = prev_date_in_str.split("/");
        prev_Month=Integer.parseInt(prevcheckdate[0]);
        prev_Day=Integer.parseInt(prevcheckdate[1]);
        prev_Year=Integer.parseInt(prevcheckdate[2]);

        Log.i(TAG,"A month ago's date is: "+prev_date_in_str);
    }
    private void whatDayIsThirtyDaysPreviousOfThis(String date_in_str){

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA);
        String[] dates=date_in_str.split("/");
        Date date=new Date(Integer.parseInt(dates[2]),Integer.parseInt(dates[0]),Integer.parseInt(dates[1]));
        date.setMonth( date.getMonth() - 1 );
        prev_date_in_str=df.format(date);
        String[] prevcheckdate = prev_date_in_str.split("/");
        prev_Month=Integer.parseInt(prevcheckdate[0]);
        prev_Day=Integer.parseInt(prevcheckdate[1]);
        prev_Year=Integer.parseInt(prevcheckdate[2]);

        Log.i(TAG,"A month ago's date is: "+prev_date_in_str);
    }

    private long countDays(String[] first, String[] last) {
        Date dateOne=new Date(Integer.parseInt(first[1]), Integer.parseInt(first[2]),Integer.parseInt(first[0]));
        Date dateTwo=new Date(Integer.parseInt(last[1]), Integer.parseInt(last[2]),Integer.parseInt(last[0]));
        long timeOne = dateOne.getTime();
        long timeTwo = dateTwo.getTime();
        long oneDay = 1000 * 60 * 60 * 24;
        long delta = (timeTwo - timeOne) / oneDay;
        return delta;
    }

    private boolean isBetween(String date,String firstDate, String lastDate){
        String[] checkdate = date.split("/");
        String[] first = firstDate.split("/");
        String[] last = lastDate.split("/");
        Log.i(TAG,"Checked date: "+date);

        Date date1=new Date(Integer.parseInt(first[2]),Integer.parseInt(first[0]),Integer.parseInt(first[1]));
        Date date2=new Date(Integer.parseInt(last[2]),Integer.parseInt(last[0]),Integer.parseInt(last[1]));
        Date datemid=new Date(Integer.parseInt(checkdate[2]),Integer.parseInt(checkdate[0]),Integer.parseInt(checkdate[1]));

        if(datemid.before(date2)&&datemid.after(date1)){
            return true;
        }
        return false;
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

    private void setUpButtons() {
        Button backbtn=(Button)findViewById(R.id.btnBackYearly);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button switchbtn= (Button) findViewById(R.id.btnSwitchViewYearly);
        switchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(barChart.getVisibility() == View.VISIBLE && chart.getVisibility() == View.INVISIBLE) {
                    chart.setVisibility(View.VISIBLE);
                    barChart.setVisibility(View.INVISIBLE);
                } else {
                    barChart.setVisibility(View.VISIBLE);
                    chart.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, LastYearActivity.class);
    }
}
