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
    public static final int REQUEST_ADD_UTILITY = 1;

    ArrayList<Utility> utilities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_utilities);

        setupButtons();
        setupList();
    }

    private void setupList() {
        UtilitySingleton singleton_utility = UtilitySingleton.getInstance();
        Log.i("temp_utility = ", singleton_utility.getName());

        //String name, String gasType, double amounts, int num_people, double emission, String startDate, String endDate


        ListView utilityList = (ListView) findViewById(R.id.utilities_listView);
        ArrayAdapter<Utility> adapter = new ArrayAdapter<Utility>(this, R.layout.layout_for_list, utilities);
        utilityList.setAdapter(adapter);
        utilityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

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