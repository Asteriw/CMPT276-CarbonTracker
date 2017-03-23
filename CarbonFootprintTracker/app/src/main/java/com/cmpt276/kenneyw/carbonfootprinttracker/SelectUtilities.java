package com.cmpt276.kenneyw.carbonfootprinttracker;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectUtilities extends AppCompatActivity {

    public static final int  SELECT_UTILITY= 1;
    private static final String TAG = "CarbonFootprintTracker";
    private static final String SHAREDPREF_SET = "CarbonFootprintTrackerUtilities";
    private static final String SHAREDPREF_ITEM_AMOUNTOFUTILITIES = "AmountOfUtilities";
    private static final String NAME ="name";
    private static final String GASTYPE="gasType";
    private static final String AMOUNT="amount";
    private static final String NUMPEOPLE="numofPeople";
    private static final String STARTDATE="startDate";
    private static final String ENDDATE="endDate";
    private static final String EMISSION = "emission";
    public static final int EDIT_UTILITY = 4;
    public static final String POS_TO_EDIT = "POS";

    ArrayList<Utility> utilities = new ArrayList<>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_utilities);
        utilities=loadUtilities();
        setupList();
        setupButtons();
    }

    private ArrayList<Utility> loadUtilities() {
        ArrayList<Utility> utils = new ArrayList<>();
        SharedPreferences pref=getSharedPreferences(SHAREDPREF_SET,MODE_PRIVATE);
        int utilityAmt=pref.getInt(SHAREDPREF_ITEM_AMOUNTOFUTILITIES,0);
        for(int i=0;i<utilityAmt;i++) {
            Utility newUtil=new Utility(pref.getString(i+NAME,""),
                    pref.getString(i+GASTYPE,""),Double.longBitsToDouble(pref.getLong(i+AMOUNT,0)),pref.getInt(i+NUMPEOPLE,0),
                    Double.longBitsToDouble(pref.getLong(i+EMISSION,0)),pref.getString(i+STARTDATE,""),pref.getString(i+ENDDATE,""));
            utils.add(newUtil);
        }
        return utils;
    }
    private void saveUtilities() {
        SharedPreferences pref=getSharedPreferences(SHAREDPREF_SET,MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        int utilityAmt=utilities.size();
        for(int i=0;i<utilityAmt;i++) {
            editor.putString(i+NAME,utilities.get(i).getName());
            editor.putString(i+GASTYPE,utilities.get(i).getGasType());
            editor.putString(i+STARTDATE,utilities.get(i).getStartDate());
            editor.putString(i+ENDDATE,utilities.get(i).getEndDate());
            editor.putInt(i+NUMPEOPLE,utilities.get(i).getNumofPeople());
            editor.putLong(i+EMISSION,Double.doubleToRawLongBits(utilities.get(i).getEmission()));
            editor.putLong(i+AMOUNT,Double.doubleToRawLongBits(utilities.get(i).getAmount()));
        }
        editor.putInt(SHAREDPREF_ITEM_AMOUNTOFUTILITIES,utilityAmt);
        editor.apply();
    }

    private void setupList() {

        final ListView utilityList = (ListView) findViewById(R.id.utilities_listView);
        ArrayAdapter<Utility> adapter = new ArrayAdapter<>(this, R.layout.layout_for_list, utilities);
        utilityList.setAdapter(adapter);
        utilityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager=getSupportFragmentManager();
                CalculationDialog dialog = new CalculationDialog();
                Bundle bundle =new Bundle();
                bundle.putDouble("CO2", utilities.get(position).getEmission());
                dialog.setArguments(bundle);
                dialog.show(manager,"CalculateDialog");
            }
        });

        registerForContextMenu(utilityList);
    }

    //Context Menu Code taken and modified from:
    //https://www.mikeplate.com/2010/01/21/show-a-context-menu-for-long-clicks-in-an-android-listview/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.utilities_listView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(utilities.get(info.position).toString());
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        int pos = info.position;
        if (menuItemName.equals("Edit")) {
            Intent i=AddUtility.makeIntent(SelectUtilities.this);
            i.putExtra(POS_TO_EDIT,pos);
            startActivityForResult(i, EDIT_UTILITY);
        } else if (menuItemName.equals("Delete")) {
            Utility u = utilities.get(pos);
            Toast.makeText(SelectUtilities.this, "Removed Utility " + u.getName(), Toast.LENGTH_SHORT).show();
            utilities.remove(u);
            setupList();
        }
        return true;
    }

    private void setupButtons() {
        Button backButton = (Button) findViewById(R.id.utility_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUtilities();
                finish();
            }
        });

        Button addButton = (Button) findViewById(R.id.add_utility);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Utilities2Add = AddUtility.makeIntent(SelectUtilities.this);
                startActivityForResult(Utilities2Add,SELECT_UTILITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case SELECT_UTILITY:
                if (resultCode == RESULT_OK) {
                    UtilitySingleton utility2Load = UtilitySingleton.getInstance();
                    Utility temp_utility = new Utility(
                            utility2Load.getName(),
                            utility2Load.getGasType(),
                            utility2Load.getAmounts(),
                            utility2Load.getNum_poeople(),
                            utility2Load.getEmission(),
                            utility2Load.getStartDate(),
                            utility2Load.getEndDate() );
                    utilities.add(temp_utility);
                    setupButtons();
                    setupList();
                }
                else{
                    setupButtons();
                    setupList();
                }
                break;
            case EDIT_UTILITY:
                if(resultCode==RESULT_OK) {
                    int pos=data.getIntExtra(POS_TO_EDIT,0);
                    Utility utilToEdit=utilities.get(pos);
                    UtilitySingleton utili = UtilitySingleton.getInstance();
                    utilToEdit.setNumofPeople( utili.getNum_poeople());
                    utilToEdit.setAmount( utili.getAmounts());
                    utilToEdit.setGasType( utili.getGasType());
                    utilToEdit.setName( utili.getName());
                    utilToEdit.setEmission( utili.getEmission());
                    utilToEdit.setStartDate( utili.getStartDate());
                    utilToEdit.setEndDate( utili.getEndDate());

                    setupList();
                    setupButtons();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        saveUtilities();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, SelectUtilities.class);
    }
}