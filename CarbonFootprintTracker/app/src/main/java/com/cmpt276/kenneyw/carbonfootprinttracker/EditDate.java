package com.cmpt276.kenneyw.carbonfootprinttracker;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
public class EditDate extends AppCompatActivity {
    String date_in_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_date);
        // Get the date of the journey
        TextView initialDateText = (TextView) findViewById(R.id.initial_date_text);
        initialDateText.setText("");
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        dp.init(2017, 1, 1, onDateChanged);
        setupButton();
    }
    private void setupButton() {
        Button ok_button = (Button) findViewById(R.id.ok_button_eidt_date);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the date and go back to journey list
                DateSingleton.getInstance().setDateString(date_in_str);
                //Intent EditDate2SelectJourneyIntent = SelectJourney.makeIntent(EditDate.this);
                //startActivity(EditDate2SelectJourneyIntent);
                finish();
            }
        });
    }
    DatePicker.OnDateChangedListener onDateChanged = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date_in_str = monthOfYear + "/" + dayOfMonth + "/" + year;
            //Toast.makeText(getApplicationContext(), date_in_str, Toast.LENGTH_LONG).show();
            TextView dateText = (TextView) findViewById(R.id.date_text);
            dateText.setText(date_in_str);
        }
    };
    public static Intent makeIntent(Context context) {
        return new Intent(context, EditDate.class);
    }
}