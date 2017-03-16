package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectJourney extends AppCompatActivity {


    ArrayList<Journey> journeyArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_journey);

        journeyArrayList=loadJourneys();
        setupAddJourneyButton();
        setupBackButton();
        setJourneyList();
        registerClickCallBack();
    }

    private ArrayList<Journey> loadJourneys() {
        ArrayList<Journey> journeyArrayList=new ArrayList<>();


        return journeyArrayList;
    }

    private void setupBackButton() {
        Button back_button =  (Button) findViewById(R.id.back_button_select_journey);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                startActivity(SelectJourney2SelectCar);
            }
        });
    }

    private void setJourneyList() {
        // Create list of items
        String[] myJourneys = {"Journey 1", "Journey 2"};

        // Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,                           // Context for the activity
                R.layout.layout_for_list,        // Layout to use
                myJourneys);                    // items to display

        // Conofigure the list view
        ListView list = (ListView) findViewById(R.id.journeyList);
        list.setAdapter(adapter);
    }

    // Upon a click on an item in the list, shows a dialog of the result of CO2 emission calculation
    private void registerClickCallBack() {

        ListView list = (ListView) findViewById(R.id.journeyList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Opens a dialog with the calculation result of CO2 emission
                FragmentManager manager=getSupportFragmentManager();
                CalculationDialog dialog = new CalculationDialog();

                Bundle bundle =new Bundle();
                bundle.putString("gasType",journeyArrayList.get(position).getGasType());
                bundle.putInt("city",journeyArrayList.get(position).getCityDistance());
                bundle.putInt("highway",journeyArrayList.get(position).getHighwayDistance());
                bundle.putDouble("mpgCity",journeyArrayList.get(position).getMpgCity());
                bundle.putDouble("mpgHighway",journeyArrayList.get(position).getMpgHighway());
                bundle.putDouble("literEngine",journeyArrayList.get(position).getLiterEngine());

                dialog.setArguments(bundle);

                dialog.show(manager,"CalculateDialog");


            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectJourney.class);
    }
}