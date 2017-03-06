package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.Toast;

import java.util.ArrayList;

public class SelectRoute extends AppCompatActivity {

    private static final String TAG = "CarbonFootprintTracker";
    ArrayList<Route> routes=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerForRoutes);
        EditText editName=(EditText)findViewById(R.id.editName);
        EditText editCity=(EditText)findViewById(R.id.editCity);
        EditText editHighway=(EditText)findViewById(R.id.editHighway);

        Log.e(TAG,"reached onCreate");
        setupButtons();
        Log.e(TAG,"set up buttons");
        setUpSpinner();
        Log.e(TAG,"set up spinner");
    }

    public void setUpSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinnerForRoutes);
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

                EditText editName=(EditText)findViewById(R.id.editName);
                EditText editCity=(EditText)findViewById(R.id.editCity);
                EditText editHighway=(EditText)findViewById(R.id.editHighway);

                editName.setText(nameToPass);
                editCity.setText(""+cityToPass);
                editHighway.setText(""+highwayToPass);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setupButtons() {

        Button calculateButton = (Button) findViewById(R.id.btncalculation);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, " Moving on to calculation activity.");

                Intent calculateIntent = ViewTracked.makeIntent(SelectRoute.this);

                EditText editName=(EditText)findViewById(R.id.editName);
                EditText editCity=(EditText)findViewById(R.id.editCity);
                EditText editHighway=(EditText)findViewById(R.id.editHighway);
                String nameToPass=editName.getText().toString();
                int cityToPass=Integer.parseInt(editCity.getText().toString());
                int highwayToPass=Integer.parseInt(editHighway.getText().toString());

                calculateIntent.putExtra("name",nameToPass);
                calculateIntent.putExtra("city",cityToPass);
                calculateIntent.putExtra("highway",highwayToPass);

                startActivity(calculateIntent);
                finish();

            }
        });

        Button deleteRoute=(Button)findViewById(R.id.btnDelete);
        deleteRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner spinner = (Spinner) findViewById(R.id.spinnerForRoutes);
                if (spinner.getSelectedItem() != null) {
                    Route r=routes.get(spinner.getSelectedItemPosition());
                    Toast.makeText(SelectRoute.this,"Removed Route"+r.toString(),Toast.LENGTH_SHORT).show();
                    routes.remove(r);
                    setUpSpinner();
                }
                else{
                    Toast.makeText(SelectRoute.this,"No Route selected to Delete",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button editRoute=(Button)findViewById(R.id.btnEdit);
        editRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) findViewById(R.id.spinnerForRoutes);
                if (spinner.getSelectedItem() != null) {

                    final Route r=routes.get(spinner.getSelectedItemPosition());
                    Toast.makeText(SelectRoute.this,"Enter new values for Route "+r.toString(),Toast.LENGTH_SHORT).show();

                    String nameToEdit=r.getRouteName();
                    int cityToEdit=r.getCityDistance();
                    int highwayToEdit=r.getHighwayDistance();

                    Bundle bundle =new Bundle();
                    bundle.putString("name",nameToEdit);
                    bundle.putInt("city",cityToEdit);
                    bundle.putInt("highway",highwayToEdit);
                    bundle.putInt("pos",spinner.getSelectedItemPosition());

                    FragmentManager manager=getSupportFragmentManager();
                    EditRouteFragment dialog=new EditRouteFragment();
                    dialog.setArguments(bundle);

                    dialog.show(manager,"EditRouteDialog");

                    Log.i(TAG,"Launched Dialog Fragment");
                }
                else{
                    Toast.makeText(SelectRoute.this,"No Route selected to Edit",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button addRoute=(Button)findViewById(R.id.btnAddRoute);
        addRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName=(EditText)findViewById(R.id.editName);
                EditText editCity=(EditText)findViewById(R.id.editCity);
                EditText editHighway=(EditText)findViewById(R.id.editHighway);

                String nameToAdd=editName.getText().toString();
                int cityToAdd=Integer.parseInt(editCity.getText().toString());
                int highwayToAdd=Integer.parseInt(editHighway.getText().toString());

                if(nameToAdd.equals("")){
                    Toast.makeText(SelectRoute.this,"Name cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else if(cityToAdd<0 || highwayToAdd<0) {
                    Toast.makeText(SelectRoute.this,"Length cannot be negative",Toast.LENGTH_SHORT).show();
                }
                else{
                    Route r=new Route(nameToAdd,cityToAdd,highwayToAdd);
                    routes.add(r);
                    setUpSpinner();
                }
            }
        });
    }



    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectRoute.class);
    }

    public void changeRoute(int pos, String name, int city, int highway) {
        routes.get(pos).setRouteName(name);
        routes.get(pos).setCityDistance(city);
        routes.get(pos).setHighwayDistance(highway);
    }
}
