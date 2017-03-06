package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectRoute extends AppCompatActivity {

    private static final String TAG = "CarbonFootprintTracker";
    ArrayList<Route> routes=new ArrayList<>();

    Spinner spinner = (Spinner) findViewById(R.id.spinnerForRoutes);
    EditText editName=(EditText)findViewById(R.id.editTextName);
    EditText editCity=(EditText)findViewById(R.id.editTextCity);
    EditText editHighway=(EditText)findViewById(R.id.editTextHighway);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        setupButtons();
        setUpSpinner();
    }

    private void setUpSpinner() {


        ArrayAdapter<Route> adapter = new ArrayAdapter<Route>(this, android.R.layout.simple_spinner_item, routes);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, routes.get(position).toString()+" selected.");
                Route r=routes.get(position);

                String nameToPass=r.getRouteName();
                int cityToPass=r.getCityDistance();
                int highwayToPass=r.getHighwayDistance();

                editName.setText(nameToPass);
                editCity.setText(""+cityToPass);
                editHighway.setText(""+highwayToPass);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
    }

    private void setupButtons() {

        Button calculateButton = (Button) findViewById(R.id.btncalculation);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculateIntent = ViewTracked.makeIntent(SelectRoute.this);
                if (spinner.getSelectedItem() != null) {

                    String nameToPass=editName.getText().toString();
                    int cityToPass=Integer.parseInt(editCity.getText().toString());
                    int highwayToPass=Integer.parseInt(editHighway.getText().toString());

                    calculateIntent.putExtra("name",nameToPass);
                    calculateIntent.putExtra("city",cityToPass);
                    calculateIntent.putExtra("highway",highwayToPass);

                    startActivity(calculateIntent);
                    finish();
                }
            }
        });

        Button deleteRoute=(Button)findViewById(R.id.btnDelete);
        deleteRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinner.getSelectedItem() != null) {
                    Route r=routes.get((int)spinner.getSelectedItemId());
                    Toast.makeText(SelectRoute.this,"Removed Route"+r.toString(),Toast.LENGTH_SHORT).show();
                    routes.remove(r);

                }
                else{
                    Toast.makeText(SelectRoute.this,"No Route selected to Delete",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button addRoute=(Button)findViewById(R.id.btnAddRoute);
        addRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameToAdd=editName.getText().toString();
                int cityToAdd=Integer.parseInt(editCity.getText().toString());
                int highwayToAdd=Integer.parseInt(editHighway.getText().toString());
                Route r=new Route(nameToAdd,cityToAdd,highwayToAdd);

                routes.add(r);
                setUpSpinner();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectRoute.class);
    }
}
