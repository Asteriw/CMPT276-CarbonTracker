package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRoute extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        setupCancelButton();
        setupOKButton();

    }

    private void setupOKButton() {

        Button okBtn=(Button)findViewById(R.id.btn_ok_addroute);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName=(EditText)findViewById(R.id.editText_name_addroute);
                EditText editCity=(EditText)findViewById(R.id.editText_city_addroute);
                EditText editHighway=(EditText)findViewById(R.id.editText_highway_addroute);

                String nameToAdd=editName.getText().toString();
                String cityToAdd=editCity.getText().toString();
                String highwayToAdd=editHighway.getText().toString();

                if(nameToAdd.equals("")){
                    Toast.makeText(AddRoute.this,"Name cannot be empty",Toast.LENGTH_SHORT).show();
                }

                else if(cityToAdd.equals("") || highwayToAdd.equals("")) {
                    Toast.makeText(AddRoute.this,"Length cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(cityToAdd)<0||Integer.parseInt(highwayToAdd)<0){
                    Toast.makeText(AddRoute.this,"Length cannot be negative",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent=new Intent();
                    intent.putExtra("name",nameToAdd);
                    intent.putExtra("city",cityToAdd);
                    intent.putExtra("highway",highwayToAdd);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private void setupCancelButton() {
        Intent intent=new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectRoute.class);
    }
}
