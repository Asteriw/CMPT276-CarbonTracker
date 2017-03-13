package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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
    private static final String SHAREDPREF_SET = "CarbonFootprintTracker";
    private static final String SHAREDPREF_ITEM_AMOUNTOFROUTES = "AmountOfRoutes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routes=LoadRoutes();
        setContentView(R.layout.activity_select_route);
        setUpSpinner();
        setupAddRouteButton();
        setupBackButton();
        setupEditRouteButton();
        setupDeleteRouteButton();
        setupSelectRouteButton();
    }

    private ArrayList<Route> LoadRoutes() {
        ArrayList<Route> routes=new ArrayList<>();
        SharedPreferences pref=getSharedPreferences(SHAREDPREF_SET,MODE_PRIVATE);
        int routeAmt=pref.getInt(SHAREDPREF_ITEM_AMOUNTOFROUTES,0);
        String routeName;int routeCity;int routeHighway;

        for(int i=0;i<routeAmt;i++) {
            routeCity=pref.getInt(i+"city",0);
            routeHighway=pref.getInt(i+"highway",0);
            routeName=pref.getString(i+"name","No Name");
            Route newRoute=new Route(routeName,routeCity,routeHighway);
            routes.add(newRoute);
        }

        return routes;
    }

    public void setUpSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.spinner_routes);
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

                EditText editName=(EditText)findViewById(R.id.editText_route_name);
                EditText editCity=(EditText)findViewById(R.id.editText_route_city);
                EditText editHighway=(EditText)findViewById(R.id.editText_route_highway);

                editName.setText(nameToPass);
                editCity.setText(""+cityToPass);
                editHighway.setText(""+highwayToPass);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setupAddRouteButton() {
        Button addRoute=(Button)findViewById(R.id.add_route_button);
        addRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName=(EditText)findViewById(R.id.editName);
                EditText editCity=(EditText)findViewById(R.id.editCity);
                EditText editHighway=(EditText)findViewById(R.id.editHighway);

                String nameToAdd=editName.getText().toString();
                String cityToAdd=editCity.getText().toString();
                String highwayToAdd=editHighway.getText().toString();

                if(nameToAdd.equals("")){
                    Toast.makeText(SelectRoute.this,"Name cannot be empty",Toast.LENGTH_SHORT).show();
                }

                else if(cityToAdd.equals("") || highwayToAdd.equals("")) {
                    Toast.makeText(SelectRoute.this,"Length cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(cityToAdd)<0||Integer.parseInt(highwayToAdd)<0){
                    Toast.makeText(SelectRoute.this,"Length cannot be negative",Toast.LENGTH_SHORT).show();
                }
                else{
                    Route r=new Route(nameToAdd,Integer.parseInt(cityToAdd),Integer.parseInt(highwayToAdd));
                    routes.add(r);
                    setUpSpinner();
                }
            }
        });
    }

    private void setupDeleteRouteButton() {
        Button deleteRoute=(Button)findViewById(R.id.btn_route_delete);
        deleteRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner spinner = (Spinner) findViewById(R.id.spinner_routes);
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
    }

    private void setupEditRouteButton() {
        Button editRoute=(Button)findViewById(R.id.btn_route_edit);
        editRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner) findViewById(R.id.spinner_routes);
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
    }

    private void setupSelectRouteButton() {
        Button selectRoute=(Button)findViewById(R.id.select_route_button);
        selectRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectRoute2selectJourney = SelectJourney.makeIntent(SelectRoute.this);

                EditText editName=(EditText)findViewById(R.id.editName);
                EditText editCity=(EditText)findViewById(R.id.editCity);
                EditText editHighway=(EditText)findViewById(R.id.editHighway);

                String nameToPass=editName.getText().toString();
                String cityToPass=editCity.getText().toString();
                String highwayToPass=editHighway.getText().toString();

                //RouteSingleton newRouteSingleton=new RouteSingleton();

                if(nameToPass.equals("")){
                    Toast.makeText(SelectRoute.this,"Name cannot be empty",Toast.LENGTH_SHORT).show();
                }

                else if(cityToPass.equals("") || highwayToPass.equals("")) {
                    Toast.makeText(SelectRoute.this,"Length cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(cityToPass)<0||Integer.parseInt(highwayToPass)<0){
                    Toast.makeText(SelectRoute.this,"Length cannot be negative",Toast.LENGTH_SHORT).show();
                }
                else {
                    selectRoute2selectJourney.putExtra("name", nameToPass);
                    selectRoute2selectJourney.putExtra("city", Integer.parseInt(cityToPass));
                    selectRoute2selectJourney.putExtra("highway", Integer.parseInt(highwayToPass));
                    startActivity(selectRoute2selectJourney);
                    finish();
                }
            }
        });
    }
    private void setupBackButton() {
        Button back_button = (Button) findViewById(R.id.back_button_select_route);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences pref=getSharedPreferences(SHAREDPREF_SET,MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
                editor.clear();
                int routeAmt=routes.size();

                for(int i=0;i<routeAmt;i++){
                    editor.putString(i+"name",routes.get(i).getRouteName());
                    editor.putInt(i+"city",routes.get(i).getCityDistance());
                    editor.putInt(i+"highway",routes.get(i).getCityDistance());
                }
                editor.apply();

                finish();
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
