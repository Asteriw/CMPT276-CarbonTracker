package com.cmpt276.kenneyw.carbonfootprinttracker;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SelectRoute extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);
        setupAddRouteButton();
        setupBackButton();
        setRouteList();
    }

    private void setupAddRouteButton() {
        Button add_a_new_route_button = (Button) findViewById(R.id.add_route_button);
        add_a_new_route_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SelectRoute2AddRoute = AddRoute.makeIntent(SelectRoute.this);
                startActivity(SelectRoute2AddRoute);
            }
        });
    }

    private void setupBackButton() {
        Button back_button = (Button) findViewById(R.id.back_button_select_route);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setRouteList() {
        // Create list of items
        String[] myRoutes = {"Route 1", "Route 2"};

        // Build Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,                       // Context for the activity
                R.layout.layout_for_list,   // Layout to use
                myRoutes);                  // items to display

        // Conofigure the list view
        ListView list = (ListView) findViewById(R.id.routeList);
        list.setAdapter(adapter);
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectRoute.class);
    }
}
