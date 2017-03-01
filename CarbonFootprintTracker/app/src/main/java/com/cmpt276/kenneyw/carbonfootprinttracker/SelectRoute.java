package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectRoute extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route);

        setupCalculateButton();
    }

    private void setupCalculateButton() {
        Button calculateButton = (Button) findViewById(R.id.btncalculation);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calculateIntent = ViewTracked.makeIntent(SelectRoute.this);
                startActivity(calculateIntent);
            }
        });
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectRoute.class);
    }
}
