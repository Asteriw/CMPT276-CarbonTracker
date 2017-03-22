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

    private static final String TAG = "CarbonFootprintTracker";
    public static final int REQUEST_ADD_UTILITY = 7;

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
        loadUtility();

        ListView utilityList = (ListView) findViewById(R.id.utilities_listView);
        ArrayAdapter<Utility> adapter = new ArrayAdapter<>(this, R.layout.layout_for_list, utilities);
        utilityList.setAdapter(adapter);
        utilityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void loadUtility(){

        Utility temp_utility = new Utility(
                UtilitySingleton.getInstance().getName(),
                UtilitySingleton.getInstance().getGasType(),
                UtilitySingleton.getInstance().getAmounts(),
                UtilitySingleton.getInstance().getNum_poeople(),
                UtilitySingleton.getInstance().getEmission(),
                UtilitySingleton.getInstance().getStartDate(),
                UtilitySingleton.getInstance().getEndDate()
        );

        Log.i("SELECT UTILITY = ", temp_utility.getName());

        utilities.add(temp_utility);
    }

/*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_ADD_UTILITY:
                if (resultCode == RESULT_CANCELED) {
                    Log.i(TAG, "User Cancelled Add Utility");
                } else {
                }
                break;
        }
    }
*/
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
                startActivity(Utilities2Add);
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectUtilities.class);
    }
}