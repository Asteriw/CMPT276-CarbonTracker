package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/*
*  This class displays the calculation result of CO2 emission of a selected journey
* */
public class CalculationDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //create view

        View viewer = LayoutInflater.from(getActivity()).inflate(R.layout.activity_calculation_dialog, null);
        TextView txtCO2=(TextView) viewer.findViewById(R.id.calculation_result_dialog);
        Double CO2=getArguments().getDouble("CO2");
        txtCO2.setText("   " + CO2 + " Kg/L");
        txtCO2.setTextColor(Color.rgb(255,178,102));
        //create button listeners
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;
                }
            }
        };

        //build alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("CO2 Emission Result")
                .setView(viewer)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }

}