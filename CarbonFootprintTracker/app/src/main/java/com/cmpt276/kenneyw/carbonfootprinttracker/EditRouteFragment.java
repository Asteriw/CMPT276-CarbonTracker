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


public class EditRouteFragment extends AppCompatDialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int pos=getArguments().getInt("pos");



        //create view
        View viewer= LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_edit_route,null);
        final EditText editName=(EditText)viewer.findViewById(R.id.editName);
        final EditText editCity=(EditText)viewer.findViewById(R.id.editCity);
        final EditText editHighway=(EditText)viewer.findViewById(R.id.editHighway);

        String nameToEdit=getArguments().getString("name");
        int cityToEdit=getArguments().getInt("city");
        int highwayToEdit=getArguments().getInt("highway");

        editName.setText(nameToEdit);
        editCity.setText(""+cityToEdit);
        editHighway.setText(""+highwayToEdit);
        //create button listeners
        DialogInterface.OnClickListener listener= new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case DialogInterface.BUTTON_POSITIVE:



                        String name=editName.getText().toString();
                        int city=Integer.parseInt(editCity.getText().toString());
                        int highway=Integer.parseInt(editHighway.getText().toString());

                        if(name.equals("")||city<0||highway<0){
                            break;
                        }

                        ((SelectRoute)getActivity()).changeRoute(pos,name,city,highway);
                        ((SelectRoute)getActivity()).setUpSpinner();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }


            }
        };
        //build alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Edit Route")
                .setView(viewer)
                .setPositiveButton(android.R.string.ok,listener)
                .setNegativeButton(android.R.string.cancel,listener)
                .create();
    }
}