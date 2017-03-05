// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter extends AppCompatActivity{

	/////////////////////////////////////////////////////////////////////
	//	Constants & Data
	/////////////////////////////////////////////////////////////////////
	// For logging:
	private static final String TAG = "DBAdapter";
	
	// DB Fields
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	public static final String KEY_ID = "id";
	public static final String KEY_MAKE = "make";
	public static final String KEY_MODEL = "model";
	public static final String KEY_CITYMPG = "cityMPG";
	public static final String KEY_HIGHWAYMPG = "highwayMPG";
	public static final String KEY_COMBMPG = "combinedMPG";
	public static final String KEY_CITYMPG_CD = "cityMPGCD";
	public static final String KEY_HIGHWAYMPG_CD = "highwayMPGCD";
	public static final String KEY_COMBMPG_CD = "combinedMPGCD";
	public static final String KEY_CITYKWH_E = "cityKWHE";
	public static final String KEY_HIGHWAYKWH_E = "highwayKWHE";
	public static final String KEY_COMBKWH_E = "combinedKWHE";
	public static final String KEY_FUELCOST = "fuelCost";
	public static final String KEY_CO2TAILPIPEGPM = "CO2TailpipeGPM";
	public static final String KEY_CYLINDERS = "cylinders";
	public static final String KEY_ENGINELITERS = "engineLiters";
	public static final String KEY_FUELTYPE = "fuelType";
	public static final String KEY_HYBRID = "hybrid";
	public static final String KEY_TRANSMISSION = "transmission";
	public static final String KEY_VEHICLECLASS = "vehicleClass";
	public static final String KEY_YEAR = "year";
	public static final String KEY_TURBOCHARGER = "turboCharger";
	public static final String KEY_SUPERCHARGER = "superCharger";
	public static final String KEY_DIFFERENTFUEL = "differentFuel";

	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_ID = 1;
	public static final int COL_MAKE = 2;
	public static final int COL_MODEL = 3;
	public static final int COL_CITYMPG = 4;
	public static final int COL_HIGHWAYMPG = 5;
	public static final int COL_COMBMPG = 6;
	public static final int COL_CITYMPG_CD = 7;
	public static final int COL_HIGHWAYMPG_CD = 8;
	public static final int COL_COMBMPG_CD = 9;
	public static final int COL_CITYKWH_E = 10;
	public static final int COL_HIGHWAYKWH_E = 11;
	public static final int COL_COMBKWH_E = 12;
	public static final int COL_FUELCOST = 13;
	public static final int COL_CO2TAILGATEGPM = 14;
	public static final int COL_CYLINDERS = 15;
	public static final int COL_ENGINELITERS = 16;
	public static final int COL_FUELTYPE = 17;
	public static final int COL_HYBRID = 18;
	public static final int COL_TRANSMISSION = 19;
	public static final int COL_VEHICLECLASS = 20;
	public static final int COL_YEAR = 21;
	public static final int COL_TURBOCHARGER = 22;
	public static final int COL_SUPERCHARGER = 23;
	public static final int COL_DIFFERENTFUEL = 24;


	public static final String[] ALL_KEYS = new String[] {
            KEY_ROWID, KEY_ID,
            KEY_MAKE, KEY_MODEL,
            KEY_CITYMPG, KEY_HIGHWAYMPG,
            KEY_COMBMPG, KEY_CITYMPG_CD,
            KEY_HIGHWAYMPG_CD, KEY_COMBMPG_CD,
            KEY_CITYKWH_E, KEY_HIGHWAYKWH_E,
            KEY_COMBKWH_E, KEY_FUELCOST,
            KEY_CO2TAILPIPEGPM, KEY_CYLINDERS,
            KEY_ENGINELITERS, KEY_FUELTYPE,
            KEY_HYBRID, KEY_TRANSMISSION,
            KEY_VEHICLECLASS, KEY_YEAR,
            KEY_TURBOCHARGER, KEY_SUPERCHARGER,
            KEY_DIFFERENTFUEL};
	
	// DB info: it's name, and the table we are using (just one).
	public static final String DATABASE_NAME = "CarDB";
	public static final String DATABASE_TABLE = "mainTable";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 3;
	
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			
			/*
			 * CHANGE 2:
			 */
			// TODO: Place your fields here!
			// + KEY_{...} + " {type} not null"
			//	- Key is the column name you created above.
			//	- {type} is one of: text, integer, real, blob
			//		(http://www.sqlite.org/datatype3.html)
			//  - "not null" means it is a required field (must be given a value).
			// NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
			+ KEY_ID + " integer not null, "
			+ KEY_MAKE + " string not null, "
			+ KEY_MODEL + " string not null, "
			+ KEY_CITYMPG + " real, "
			+ KEY_HIGHWAYMPG + " real, "
			+ KEY_COMBMPG + " real, "
			+ KEY_CITYMPG_CD + " real, "
			+ KEY_HIGHWAYMPG_CD + " real, "
			+ KEY_COMBMPG_CD + " real, "
			+ KEY_CITYKWH_E + " real, "
			+ KEY_HIGHWAYKWH_E + " real, "
			+ KEY_COMBKWH_E + " real, "
			+ KEY_FUELCOST + " integer, "
			+ KEY_CO2TAILPIPEGPM + " real not null, "
			+ KEY_CYLINDERS + " integer, "
			+ KEY_ENGINELITERS + " real, "
			+ KEY_FUELTYPE + " string, "
			+ KEY_HYBRID + " string, "
			+ KEY_TRANSMISSION + " string, "
			+ KEY_VEHICLECLASS + " string, "
			+ KEY_YEAR + " integer not null, "
			+ KEY_TURBOCHARGER + " string, "
			+ KEY_SUPERCHARGER + " string, "
			+ KEY_DIFFERENTFUEL + " string"
			// Rest  of creation:
			+ ");";
	
	// Context of application who uses us.
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to the database.
	public long insertRow(int id, String make, String model,
                          float cityMPG, float highwayMPG, float combinedMPG,
                          float cityMPG_CD, float highwayMPG_CD, float combinedMPG_CD,
                          float cityKWH_E, float highwayKWH_E, float combinedKWH_E,
                          int fuelCost, float co2TailpipeGPM, int cylinders,
                          float engineLiters, String fuelType, String hybrid,
                          String transmission, String vehicleClass, int year,
                          String turbocharger, String supercharger, String differentFuel) {
		/*
		 * CHANGE 3:
		 */		
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ID, id);
		initialValues.put(KEY_MAKE, make);
		initialValues.put(KEY_MODEL, model);
		initialValues.put(KEY_CITYMPG, cityMPG);
		initialValues.put(KEY_HIGHWAYMPG, highwayMPG);
		initialValues.put(KEY_COMBMPG, combinedMPG);
		initialValues.put(KEY_CITYMPG_CD, cityMPG_CD);
		initialValues.put(KEY_HIGHWAYMPG_CD, highwayMPG_CD);
		initialValues.put(KEY_COMBMPG_CD, combinedMPG_CD);
		initialValues.put(KEY_CITYKWH_E, cityKWH_E);
		initialValues.put(KEY_HIGHWAYKWH_E, highwayKWH_E);
		initialValues.put(KEY_COMBKWH_E, combinedKWH_E);
		initialValues.put(KEY_FUELCOST, fuelCost);
		initialValues.put(KEY_CO2TAILPIPEGPM, co2TailpipeGPM);
		initialValues.put(KEY_CYLINDERS, cylinders);
		initialValues.put(KEY_ENGINELITERS, engineLiters);
		initialValues.put(KEY_FUELTYPE, fuelType);
		initialValues.put(KEY_HYBRID, hybrid);
		initialValues.put(KEY_TRANSMISSION, transmission);
		initialValues.put(KEY_VEHICLECLASS, vehicleClass);
		initialValues.put(KEY_YEAR, year);
		initialValues.put(KEY_TURBOCHARGER, turbocharger);
		initialValues.put(KEY_SUPERCHARGER, supercharger);
		initialValues.put(KEY_DIFFERENTFUEL, differentFuel);
		
		// Insert it into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}
	
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId, int id, String make, String model,
                             float cityMPG, float highwayMPG, float combinedMPG,
                             float cityMPG_CD, float highwayMPG_CD, float combinedMPG_CD,
                             float cityKWH_E, float highwayKWH_E, float combinedKWH_E,
                             int fuelCost, float co2TailpipeGPM, int cylinders,
                             float engineLiters, String fuelType, String hybrid,
                             String transmission, String vehicleClass, int year,
                             String turbocharger, String supercharger, String differentFuel){
		String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
        newValues.put(KEY_ID, id);
        newValues.put(KEY_MAKE, make);
        newValues.put(KEY_MODEL, model);
        newValues.put(KEY_CITYMPG, cityMPG);
        newValues.put(KEY_HIGHWAYMPG, highwayMPG);
        newValues.put(KEY_COMBMPG, combinedMPG);
        newValues.put(KEY_CITYMPG_CD, cityMPG_CD);
        newValues.put(KEY_HIGHWAYMPG_CD, highwayMPG_CD);
        newValues.put(KEY_COMBMPG_CD, combinedMPG_CD);
        newValues.put(KEY_CITYKWH_E, cityKWH_E);
        newValues.put(KEY_HIGHWAYKWH_E, highwayKWH_E);
        newValues.put(KEY_COMBKWH_E, combinedKWH_E);
        newValues.put(KEY_FUELCOST, fuelCost);
        newValues.put(KEY_CO2TAILPIPEGPM, co2TailpipeGPM);
        newValues.put(KEY_CYLINDERS, cylinders);
        newValues.put(KEY_ENGINELITERS, engineLiters);
        newValues.put(KEY_FUELTYPE, fuelType);
        newValues.put(KEY_HYBRID, hybrid);
        newValues.put(KEY_TRANSMISSION, transmission);
        newValues.put(KEY_VEHICLECLASS, vehicleClass);
        newValues.put(KEY_YEAR, year);
        newValues.put(KEY_TURBOCHARGER, turbocharger);
        newValues.put(KEY_SUPERCHARGER, supercharger);
        newValues.put(KEY_DIFFERENTFUEL, differentFuel);
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}

    public ArrayList<Integer> getYearValues(){
        ArrayList<Integer> yearsToReturn = new ArrayList<>();
        String years = "year";
        String[] year = {KEY_YEAR};
        Cursor cursor = db.query(true, DATABASE_TABLE, year,
                null, null, null, null, years, null);
        int i = 0;
        cursor.moveToFirst();
        while(!cursor.isLast()){
            yearsToReturn.add(cursor.getInt(i));
            i++;
            cursor.moveToNext();
        }
        yearsToReturn.add(cursor.getInt(i));
        return yearsToReturn;
    }
	
	
	/////////////////////////////////////////////////////////////////////
	//	Private Helper Classes:
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * Private class which handles database creation and upgrading.
	 * Used to handle low-level database access.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
