package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class GetCarNameFragment extends AppCompatDialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //create view
        View viewer= LayoutInflater.from(getActivity()).inflate(R.layout.activity_get_name, null);

        // Get the user input
        final EditText editName=(EditText)viewer.findViewById(R.id.car_name_for_listcars_UI);

        //create button listeners
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        if(editName.getText().toString().isEmpty()){
                            Log.i("No name", "");
                            Toast.makeText(getActivity(),"Fields cannot be empty.",Toast.LENGTH_SHORT).show();
                            break;
                        }else{
                            // Get the user input
                            String user_defined_car_name = editName.getText().toString();
                            // Log.i("name = ", user_defined_car_name);

                            // Set the Car name


                        }

                        // kill the addCar Activity and close
                        getActivity().finish();
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        //build alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Enter Car Name")
                .setView(viewer)
                .setPositiveButton(android.R.string.ok,listener)
                .setNegativeButton(android.R.string.cancel,listener)
                .create();
    }
}
