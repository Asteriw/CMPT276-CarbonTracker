package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


/*
This Class stores a list of routes for perusal in a journey. Can add, edit and delete saved routes.
Includes error checking of input, and user can go back to select car screen. Saving routes via shared prefs and
transferring to journey to save via singleton class: routeSingleton
 */

public class SelectRoute extends AppCompatActivity {

    private static final String TAG = "CarbonFootprintTracker";
    public static final String NAME = "name";
    public static final String CITY = "city";
    public static final String HIGHWAY = "highway";
    public static final int REQUEST_ADD_ROUTE = 1;
    ArrayList<Route> routes=new ArrayList<>();
    private static final String SHAREDPREF_SET = "CarbonFootprintTracker";
    private static final String SHAREDPREF_ITEM_AMOUNTOFROUTES = "AmountOfRoutes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routes=LoadRoutes();
        setContentView(R.layout.activity_select_route);
        setUpListView();
        setupBackButton();
        setupAddRouteButton();
    }

    private void setupAddRouteButton() {
        Button btn=(Button)findViewById(R.id.btn_add_route);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=AddRoute.makeIntent(SelectRoute.this);
                startActivityForResult(intent, REQUEST_ADD_ROUTE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            switch (requestCode) {
                case REQUEST_ADD_ROUTE:
                    if (resultCode == RESULT_CANCELED) {
                        Log.i(TAG, "User Cancelled Add Route");
                    }
                    else{
                        String nameToAdd = data.getStringExtra("name");
                        int cityToAdd = data.getIntExtra("city", 0);
                        int highwayToAdd = data.getIntExtra("highway", 0);
                        Route r = new Route(nameToAdd, cityToAdd, highwayToAdd);
                        routes.add(r);
                        setUpListView();
                        break;
                    }
            }
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

    public void setUpListView() {

        ListView listForRoutes=(ListView)findViewById(R.id.listViewRoutes);
        ArrayAdapter<Route> adapter = new ArrayAdapter<Route>(this, R.layout.layout_for_list, routes);
        listForRoutes.setAdapter(adapter);
        listForRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e(TAG, routes.get(position).toString()+" selected.");

                Route r=routes.get(position);
                String nameToPass=r.getRouteName();
                int cityToPass=r.getCityDistance();
                int highwayToPass=r.getHighwayDistance();

                RouteSingleton routeselected=RouteSingleton.getInstance();
                routeselected.setRouteName(nameToPass);
                routeselected.setCityDistance(cityToPass);
                routeselected.setHighwayDistance(highwayToPass);

                saveRoutes();
                Intent i=new Intent();
                setResult(RESULT_OK,i);
                finish();
            }
        });
        registerForContextMenu(listForRoutes);
    }

    //Context Menu Code taken and modified from:
    //https://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        if(v.getId()==R.id.listViewRoutes){
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(routes.get(info.position).toString());
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
            launchEditFragment(pos);
        }
        else if(menuItemName.equals("Delete")){
            Route r=routes.get(pos);
            Toast.makeText(SelectRoute.this,"Removed Route "+r.getRouteName(),Toast.LENGTH_SHORT).show();
            routes.remove(r);
            setUpListView();
        }
        return true;
    }

    private void launchEditFragment(int pos) {
         Route r=routes.get(pos);
        Toast.makeText(SelectRoute.this,"Enter new values for Route "+r.toString(),Toast.LENGTH_SHORT).show();

        String nameToEdit=r.getRouteName();
        int cityToEdit=r.getCityDistance();
        int highwayToEdit=r.getHighwayDistance();

        Bundle bundle =new Bundle();
        bundle.putString("name",nameToEdit);
        bundle.putInt("city",cityToEdit);
        bundle.putInt("highway",highwayToEdit);
        bundle.putInt("pos",pos);

        FragmentManager manager=getSupportFragmentManager();
        EditRouteFragment dialog=new EditRouteFragment();
        dialog.setArguments(bundle);

        dialog.show(manager,"EditRouteDialog");

        Log.i(TAG,"Launched Dialog Fragment");
    }

    private void saveRoutes() {
        SharedPreferences pref=getSharedPreferences(SHAREDPREF_SET,MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        int routeAmt=routes.size();

        for(int i=0;i<routeAmt;i++){

            editor.putString(i+ NAME,routes.get(i).getRouteName());
            editor.putInt(i+ CITY,routes.get(i).getCityDistance());
            editor.putInt(i+ HIGHWAY,routes.get(i).getHighwayDistance());
        }
        editor.putInt(SHAREDPREF_ITEM_AMOUNTOFROUTES,routeAmt);
        editor.apply();
    }

    private void setupBackButton() {
        Button back_button = (Button) findViewById(R.id.back_button_select_route);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoutes();
                Intent i=new Intent();
                setResult(RESULT_CANCELED,i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        saveRoutes();
        Intent i=new Intent();
        setResult(RESULT_CANCELED,i);
        finish();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectRoute.class);
    }
    public void changeRoute(int pos, String name, int city, int highway) {
        Log.i(TAG,"change Route got: "+name+" "+city+" "+highway);
        routes.get(pos).setRouteName(name);
        routes.get(pos).setCityDistance(city);
        routes.get(pos).setHighwayDistance(highway);
    }
}
