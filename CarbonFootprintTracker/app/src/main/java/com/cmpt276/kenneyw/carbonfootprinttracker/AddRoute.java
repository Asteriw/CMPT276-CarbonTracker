package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddRoute extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        setupOkButton();
        setupCancelButton();
        // handleTextsfromUser();
    }
    /*
        private void handleTextsfromUser() {
            EditText editHighwayDistance = (EditText) findViewById(R.id.highway_distance_from_user_text);
            int highwayDistanceFromUser = Integer.parseInt(editHighwayDistance.getText().toString());

            EditText editCityDistance = (EditText) findViewById(R.id.city_distance_from_user_text);
            int ciityDistanceFromUser = Integer.parseInt(editCityDistance.getText().toString());

            EditText editRouteNickName = (EditText) findViewById(R.id.route_nick_name_from_user_text);
            String routeNickNameFromUser = editRouteNickName.getText().toString();
        }
    */
    private void setupCancelButton() {
        Button cancel_button = (Button) findViewById(R.id.cancel_button_add_route);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupOkButton() {
        Button ok_button = (Button) findViewById(R.id.ok_button_add_route);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // saves a new route and send it to the routelist
                finish();
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddRoute.class);
    }
}
