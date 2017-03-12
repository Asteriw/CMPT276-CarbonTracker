package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

/*
*  This class displays the calculation result of CO2 emission of a selected journey
* */
public class CalculationDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //create view
        View viewer = LayoutInflater.from(getActivity()).inflate(R.layout.activity_calculation_dialog, null);

        //create button listeners
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Don't do anything
                        break;
                }
            }
        };

        //build alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("CO2 Emission Result")
                .setView(viewer)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.cancel, listener)
                .create();
    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, CalculationDialog.class);
    }
}