package com.cmpt276.kenneyw.carbonfootprinttracker.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.cmpt276.kenneyw.carbonfootprinttracker.R;

public class SettingsActivity extends AppCompatActivity {

    public static final String SETTING = "CarbonFootprintTrackerSettings";
    public static final String TREESETTING = "TreeSetting";
    boolean setting=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSetting();
        setUpSwitcher();
        setUpButtons();
        hideNavBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void hideNavBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void getSetting() {
        SharedPreferences pref=getSharedPreferences(SETTING,MODE_PRIVATE);
        setting=pref.getBoolean(TREESETTING,false);
    }

    private void setUpButtons() {
        Button backbtn = (Button) findViewById(R.id.btnBackSettings);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button okbtn= (Button) findViewById(R.id.btnOkSettings);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch treeSwitch = (Switch) findViewById(R.id.switchTree);
                SharedPreferences pref=getSharedPreferences(SETTING,MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.putBoolean(TREESETTING,treeSwitch.isChecked());
                editor.apply();
                finish();
            }
        });
    }

    private void setUpSwitcher() {
        Switch treeSwitch = (Switch) findViewById(R.id.switchTree);
        if(setting){
            treeSwitch.setChecked(true);
        }
        else{
            treeSwitch.setChecked(false);
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }
}
