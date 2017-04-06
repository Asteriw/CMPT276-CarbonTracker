package com.cmpt276.kenneyw.carbonfootprinttracker.ui;

/**
 *Date Picker Activity, picks any day for corresponding journey
 *saves year month and day for later use
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.cmpt276.kenneyw.carbonfootprinttracker.R;
import com.cmpt276.kenneyw.carbonfootprinttracker.model.DateSingleton;
public class EditDate extends AppCompatActivity {
    String date_in_str;
    int Year;
    int Month;
    int Day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_date);

        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        dp.init(2017, 0, 1, onDateChanged);
        Year=2017;
        Month=0;
        Day=1;
        date_in_str=Month+1 + "/" + Day + "/" + Year;
        setupButtons();
    }
    private void setupButtons() {
        Button ok_button = (Button) findViewById(R.id.ok_button_edit_date);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the date and go back to journey list
                DateSingleton date=DateSingleton.getInstance();
                date.setDateString(date_in_str);
                date.setYear(Year);
                date.setMonth(Month);
                date.setDay(Day);
                Intent i=new Intent();
                setResult(RESULT_OK,i);
                finish();
            }
        });
        Button cancel_button=(Button)findViewById(R.id.cancel_button_edit_date);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                setResult(RESULT_CANCELED,i);
                finish();
            }
        });
    }
    DatePicker.OnDateChangedListener onDateChanged = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date_in_str = monthOfYear+1 + "/" + dayOfMonth + "/" + year;
            Year=year;
            Month=monthOfYear+1;
            Day=dayOfMonth;
        }
    };
    public static Intent makeIntent(Context context) {
        return new Intent(context, EditDate.class);
    }
}