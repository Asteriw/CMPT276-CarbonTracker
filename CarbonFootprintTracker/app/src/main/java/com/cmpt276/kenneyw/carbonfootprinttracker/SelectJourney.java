package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/*
This Class shows the user a list of saved journies, and can add delete and edit journies.
User can also see CO2 emitted for chosen journey in a dialog. Saves journies from route and car singletons, via shared preference.
//might transfer database mgmt. to SQL
 */

public class SelectJourney extends AppCompatActivity {

    private static final String TAG = "CarbonFootprintTracker";
    private static final int CAR_AND_ROUTE_SELECTED = 1;
    private static final int EDIT_JOURNEY = 2;
    private static final String SHAREDPREF_SET = "CarbonFootprintTrackerJournies";
    private static final String SHAREDPREF_ITEM_AMOUNTOFJOURNEYS = "AmountOfJourneys";

    public static final String NAME = "name";
    public static final String ROUTENAME = "routeName";
    public static final String CITY = "city";
    public static final String HIGHWAY = "highway";
    public static final String GASTYPE="gasType";
    public static final String MPGCITY="mpgCity";
    public static final String MPGHIGHWAY="mpgHighway";
    public static final String TRANSMISSION ="transmission";
    public static final String LITERENGINE="literEngine";
    public static final String DATEOFTRAVEL="DateOfTravel";
    public static final String TOTALEMISSIONS ="totalEmissions";
    public static final String BUS = "bus";
    public static final String BIKE = "bike";
    public static final String SKYTRAIN = "skytrain";

    int tipCounter = 0;
    String tipString = "";
    int tipIndex = 0;
    int tempIndex = 0;

    ArrayList<Journey> journeyArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_journey);

        journeyArrayList=loadJourneys();
        setupAddJourneyButton();
        setupBackButton();
        setJourneyList();
    }

    public ArrayList<Journey> loadJourneys() {
        ArrayList<Journey> journeyArrayList=new ArrayList<>();
        SharedPreferences pref=getSharedPreferences(SHAREDPREF_SET,MODE_PRIVATE);
        int journeyAmt=pref.getInt(SHAREDPREF_ITEM_AMOUNTOFJOURNEYS,0);
        for(int i=0;i<journeyAmt;i++){
            Date d=new Date(pref.getLong(i+DATEOFTRAVEL,0));
            Journey j=new Journey(
                    pref.getString(i+ROUTENAME,""),
                    pref.getInt(i+CITY,0),
                    pref.getInt(i+HIGHWAY,0),
                    pref.getString(i+NAME,""),
                    pref.getString(i+GASTYPE,""),
                    Double.longBitsToDouble(pref.getLong(i+MPGCITY,0)),
                    Double.longBitsToDouble(pref.getLong(i+MPGHIGHWAY,0)),
                    Double.longBitsToDouble(pref.getLong(i+LITERENGINE,0)),
                    d,
                    pref.getBoolean(i+BUS,false),
                    pref.getBoolean(i+BIKE,false),
                    pref.getBoolean(i+SKYTRAIN,false));
            journeyArrayList.add(j);
        }
        return journeyArrayList;
    }

    private void saveJourneys() {
        SharedPreferences pref=getSharedPreferences(SHAREDPREF_SET,MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        int journeyAmt=journeyArrayList.size();
        for(int i=0;i<journeyAmt;i++){
            editor.putString(i+ NAME,journeyArrayList.get(i).getName());
            editor.putString(i+ ROUTENAME,journeyArrayList.get(i).getRouteName());
            editor.putString(i+ GASTYPE,journeyArrayList.get(i).getGasType());
            editor.putLong(i+MPGCITY,Double.doubleToRawLongBits(journeyArrayList.get(i).getMpgCity()));
            editor.putLong(i+ MPGHIGHWAY,Double.doubleToRawLongBits(journeyArrayList.get(i).getMpgHighway()));
            editor.putInt(i+CITY,journeyArrayList.get(i).getCityDistance());
            editor.putInt(i+HIGHWAY,journeyArrayList.get(i).getHighwayDistance());
            editor.putLong(i+LITERENGINE,Double.doubleToRawLongBits(journeyArrayList.get(i).getLiterEngine()));
            editor.putLong(i+DATEOFTRAVEL,journeyArrayList.get(i).getDateOfTravel().getTime());
            editor.putLong(i+TOTALEMISSIONS,Double.doubleToRawLongBits(journeyArrayList.get(i).getTotalEmissions()));
            editor.putBoolean(i+BUS,journeyArrayList.get(i).isBus());
            editor.putBoolean(i+BIKE,journeyArrayList.get(i).isBike());
            editor.putBoolean(i+SKYTRAIN,journeyArrayList.get(i).isSkytrain());
        }
        editor.putInt(SHAREDPREF_ITEM_AMOUNTOFJOURNEYS,journeyAmt);
        editor.apply();
    }

    private void setupBackButton() {
        Button back_button =  (Button) findViewById(R.id.back_button_select_journey);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveJourneys();
                finish();
            }
        });
    }

    private void setupAddJourneyButton() {
        // Directs to "Select Car" Screen
        Button journey_button = (Button) findViewById(R.id.add_a_new_journey_button);
        journey_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SelectJourney2SelectCar = SelectCar.makeIntent(SelectJourney.this);
                startActivityForResult(SelectJourney2SelectCar,CAR_AND_ROUTE_SELECTED);
            }
        });
    }

    private void setJourneyList() {

        ArrayAdapter<Journey> adapter = new ArrayAdapter<Journey>(this, R.layout.layout_for_list, journeyArrayList);
        ListView list = (ListView) findViewById(R.id.journeyList);
        list.setAdapter(adapter);list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Opens a dialog with the calculation result of CO2 emission
                FragmentManager manager=getSupportFragmentManager();
                CalculationDialog dialog = new CalculationDialog();

                Bundle bundle =new Bundle();
                bundle.putDouble("CO2",journeyArrayList.get(position).getTotalEmissions());

                dialog.setArguments(bundle);
                dialog.show(manager,"CalculateDialog");
            }
        });
        registerForContextMenu(list);
    }

    //Context Menu Code taken and modified from:
    //https://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        if(v.getId()==R.id.journeyList){
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(journeyArrayList.get(info.position).getName()+" - "+journeyArrayList.get(info.position).getRouteName());
            String[] menuItems= getResources().getStringArray(R.array.menu);

            for(int i=0;i<menuItems.length;i++){
                menu.add(Menu.NONE,i,i,menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        int pos=info.position;

        if(menuItemName.equals("Edit")){
            Intent i=SelectCar.makeIntent(SelectJourney.this);
            i.putExtra("pos",pos);
            startActivityForResult(i, EDIT_JOURNEY);
        }
        else if(menuItemName.equals("Delete")){
            Journey j=journeyArrayList.get(pos);
            journeyArrayList.remove(j);
            setJourneyList();
        }
        return true;
    }

    //Creates an Alert Dialog using a the custom layout activity_tip_dialog
    //Clicking next tip will create a new Alert Dialog with a new message
    //The message is set by running the tipTextSelector method, which avoids repeats and selects
    //a relevant tip (if available)
    private void tipMaker() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View tipView =inflater.inflate(R.layout.activity_tip_dialog, null);

        TextView tipText = (TextView) tipView.findViewById(R.id.tip_text);
        tipText.setGravity(Gravity.CENTER);
        tipText.setText(tipTextSelector());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tips!");
        builder.setView(tipView);
        builder.setPositiveButton("Next Tip", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tipMaker();
            }
        });
        builder.setNegativeButton("Ok", null);
        AlertDialog tipDialog = builder.create();
        tipDialog.show();
    }

    private CharSequence tipTextSelector() {
        if (tipCounter==0) {
            tipString = "Tipperoni";
            tipCounter++;
            return tipString;
        }
        else if (tipCounter==1) {
            tipString = "Just the tips";
            tipCounter++;
        }
        else if (tipCounter>1) {
            tipString = "No more Tips for you!";
            tipCounter++;
        }
        return tipString;
    }

    //Avoids tips that have been shown in the last 7
    //Picks relevant tips, using userdata
    private CharSequence tipTextSelector2() {
        //if (database tipRepeat var > 0) {
            //tipIndex++;
            //database tipRepeat var++
        //}
        //if (database tipRepeat var == 7) {
            //
            //database tipRepeat var = 0;

            //
        //}
        return tipString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAR_AND_ROUTE_SELECTED:
                if(resultCode==RESULT_OK){
                    Date date=new Date();
                    RouteSingleton finalRoute=RouteSingleton.getInstance();
                    CarSingleton finalCar=CarSingleton.getInstance();
                    Journey finalJourney=new Journey(finalRoute.getRouteName(),finalRoute.getCityDistance(),finalRoute.getHighwayDistance()
                            ,finalCar.getName(),finalCar.getGasType(),finalCar.getCityEmissions(),finalCar.getHighwayEmissions(),
                            finalCar.getLiterEngine(),date,finalCar.getBus(),finalCar.getWalk(),finalCar.getSkytrain());
                    finalJourney.setTotalEmissions(finalJourney.CalculateTotalEmissions());
                    journeyArrayList.add(finalJourney);
                    setupAddJourneyButton();
                    setupBackButton();
                    setJourneyList();
                    tipMaker();
                }
                else{
                    Log.i(TAG,"User Cancelled");
                    setupAddJourneyButton();
                    setupBackButton();
                    setJourneyList();
                }

                break;
            case EDIT_JOURNEY:
                if(resultCode==RESULT_OK){
                    int pos=data.getIntExtra("pos",0);
                    Journey j=journeyArrayList.get(pos);
                    RouteSingleton finalRoute=RouteSingleton.getInstance();
                    CarSingleton finalCar=CarSingleton.getInstance();
                    j.setRouteName(finalRoute.getRouteName());
                    j.setCityDistance(finalRoute.getCityDistance());
                    j.setHighwayDistance(finalRoute.getHighwayDistance());
                    j.setName(finalCar.getName());
                    j.setGasType(finalCar.getGasType());
                    j.setMpgCity(finalCar.getCityEmissions());
                    j.setMpgHighway(finalCar.getHighwayEmissions());
                    j.setLiterEngine(finalCar.getLiterEngine());
                    j.setTotalEmissions(j.CalculateTotalEmissions());
                    setupAddJourneyButton();
                    setupBackButton();
                    setJourneyList();
                }
                else{
                    setupAddJourneyButton();
                    setupBackButton();
                    setJourneyList();
                }
                break;

        }
    }

    public void onBackPressed() {
        saveJourneys();
        finish();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectJourney.class);
    }
}