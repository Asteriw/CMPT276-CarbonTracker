package com.cmpt276.kenneyw.carbonfootprinttracker;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
public class SelectUtilities extends AppCompatActivity{
    private static final String TAG = "CarbonFootprintTracker";
    public static final int REQUEST_ADD_UTILITY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_utilities);
        setupButtons();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ADD_UTILITY:
                if (resultCode == RESULT_CANCELED) {
                    Log.i(TAG, "User Cancelled Add Utility");
                } else {
                }
        }
    }
    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectUtilities.class);
    }
}