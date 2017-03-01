package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectTransportation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transportation);

        setupRouteButton();
    }

    private void setupRouteButton() {
        Button RouteButton = (Button) findViewById(R.id.btnselectroute);
        RouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SelectRouteIntent = SelectRoute.makeIntent(SelectTransportation.this);
                startActivity(SelectRouteIntent);
            }
        });
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectTransportation.class);
    }
}
