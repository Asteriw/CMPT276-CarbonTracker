package com.cmpt276.kenneyw.carbonfootprinttracker;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SelectUtilities extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_utilities);
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectUtilities.class);
    }
}