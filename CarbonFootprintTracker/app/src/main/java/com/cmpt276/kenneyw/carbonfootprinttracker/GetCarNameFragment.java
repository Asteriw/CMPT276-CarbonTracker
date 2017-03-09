package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class GetCarNameFragment extends AppCompatDialogFragment{
    String user_defined_car_name;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //create view
        View viewer= LayoutInflater.from(getActivity()).inflate(R.layout.activity_get_name, null);

        // Get the user input
        final EditText editName=(EditText)viewer.findViewById(R.id.car_name_for_listcars_UI);

        //create button listeners
        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

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
                            user_defined_car_name = editName.getText().toString();
                            Log.i("UI name is = ", user_defined_car_name);

                            // Set the Car name
                            int getCarAtIndex = ((AddCar)getActivity()).listOfCars.countCars();
                            Log.i("getCarAtIndex = ", "" + getCarAtIndex);
                            ((AddCar)getActivity()).listOfCars.getCar( getCarAtIndex - 1).setName(user_defined_car_name);
                            Log.i("UI after name is = ",
                                    ((AddCar)getActivity()).listOfCars.getCar( getCarAtIndex - 1).getName());

                            // Check if name was set properly
                            Intent intent2SelectTransportation = SelectTransportation.makeIntent(getActivity());
                            intent2SelectTransportation.putExtra("CarDataName", ((AddCar)getActivity()).listOfCars.getCar(getCarAtIndex - 1).getName());
                            intent2SelectTransportation.putExtra("CarDataCityEmissions", ((AddCar)getActivity()).listOfCars.getCar(getCarAtIndex - 1).getCityEmissions());
                            intent2SelectTransportation.putExtra("CarDataGasType", ((AddCar)getActivity()).listOfCars.getCar(getCarAtIndex - 1).getGasType());
                            intent2SelectTransportation.putExtra("CarDataHighwayEmissions", ((AddCar)getActivity()).listOfCars.getCar(getCarAtIndex - 1).getHighwayEmissions());
                            intent2SelectTransportation.putExtra("CarDataLiterEngine", ((AddCar)getActivity()).listOfCars.getCar(getCarAtIndex - 1).getLiterEngine());
                            intent2SelectTransportation.putExtra("CarDataMake", ((AddCar)getActivity()).listOfCars.getCar(getCarAtIndex - 1).getMake());
                            intent2SelectTransportation.putExtra("CarDataModel", ((AddCar)getActivity()).listOfCars.getCar(getCarAtIndex - 1).getModel());
                            intent2SelectTransportation.putExtra("CarDataTransmission", ((AddCar)getActivity()).listOfCars.getCar(getCarAtIndex - 1).getTransmission());
                            intent2SelectTransportation.putExtra("CarDataYear", ((AddCar)getActivity()).listOfCars.getCar(getCarAtIndex - 1).getYear());
                            startActivity(intent2SelectTransportation);

                        }

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
