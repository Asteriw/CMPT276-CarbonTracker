package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EditRouteFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //create view
        View v= LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_edit_route,null);

        setupEditTexts();

        //create button listeners
        DialogInterface.OnClickListener listener= new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        EditText editName=(EditText)getActivity().findViewById(R.id.editTextName);
                        EditText editCity=(EditText)getActivity().findViewById(R.id.editTextCity);
                        EditText editHighway=(EditText)getActivity().findViewById(R.id.editTextHighway);

                        String name=editName.getText().toString();
                        int city=Integer.parseInt(editCity.getText().toString());
                        int highway=Integer.parseInt(editHighway.getText().toString());
                        if(name.equals("")||city<0||highway<0){break;}
                        int pos=getArguments().getInt("pos");
                        ((SelectRoute)getActivity()).routes.get(pos).setCityDistance(city);
                        ((SelectRoute)getActivity()).routes.get(pos).setHighwayDistance(highway);
                        ((SelectRoute)getActivity()).routes.get(pos).setRouteName(name);

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }


            }
        };
        //build alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Edit Route")
                .setView(v)
                .setPositiveButton(android.R.string.ok,listener)
                .setNegativeButton(android.R.string.cancel,listener)
                .create();
    }

    private void setupEditTexts() {
        String name=getArguments().getString("name");
        int city=getArguments().getInt("city");
        int highway=getArguments().getInt("highway");

        EditText editName=(EditText)getActivity().findViewById(R.id.editTextName);
        EditText editCity=(EditText)getActivity().findViewById(R.id.editTextCity);
        EditText editHighway=(EditText)getActivity().findViewById(R.id.editTextHighway);

        editName.setText(name);
        editCity.setText(""+city);
        editHighway.setText(""+highway);
    }
}
