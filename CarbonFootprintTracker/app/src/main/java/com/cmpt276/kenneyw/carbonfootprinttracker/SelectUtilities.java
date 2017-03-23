package com.cmpt276.kenneyw.carbonfootprinttracker;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectUtilities extends AppCompatActivity {

    public static final int  SELECT_UTILITY= 1;
    private static final String TAG = "CarbonFootprintTracker";

    ArrayList<Utility> utilities = new ArrayList<>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_utilities);

        setupList();
        setupButtons();
    }

    private void setupList() {

        ListView utilityList = (ListView) findViewById(R.id.utilities_listView);
        ArrayAdapter<Utility> adapter = new ArrayAdapter<>(this, R.layout.layout_for_list, utilities);
        utilityList.setAdapter(adapter);
        utilityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void setupButtons() {
        Button backButton = (Button) findViewById(R.id.utility_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button addButton = (Button) findViewById(R.id.add_utility);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Utilities2Add = AddUtility.makeIntent(SelectUtilities.this);
                startActivityForResult(Utilities2Add,SELECT_UTILITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case SELECT_UTILITY:
                if (resultCode == RESULT_OK) {
                    UtilitySingleton utility2Load = UtilitySingleton.getInstance();
                    Utility temp_utility = new Utility(
                            utility2Load.getName(),
                            utility2Load.getGasType(),
                            utility2Load.getAmounts(),
                            utility2Load.getNum_poeople(),
                            utility2Load.getEmission(),
                            utility2Load.getStartDate(),
                            utility2Load.getEndDate() );
                    utilities.add(temp_utility);
                    setupButtons();
                    setupList();
                }
                else{
                    setupButtons();
                    setupList();
                }
                break;
        }
    }
    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectUtilities.class);
    }
}