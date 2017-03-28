package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


//This file is used for user data, such as cars, routes and journeys/

// TO USE:
// Change the package (at top) to match your project.

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
    //CARS
    public static final String KEY_TABLE1_CARNAME = "carName";
    public static final String KEY_TABLE1_CARMAKE = "carMake";
    public static final String KEY_TABLE1_CARMODEL = "carModel";
    public static final String KEY_TABLE1_HIGHWAY = "highwayEmissions";
    public static final String KEY_TABLE1_CITY = "cityEmissions";
    public static final String KEY_TABLE1_YEAR = "year";
    public static final String KEY_TABLE1_TRANSMISSION = "transmissions";
    public static final String KEY_TABLE1_LITERENGINE = "literDisplacement";
    public static final String KEY_TABLE1_GASTYPE = "gasType";
    public static final String KEY_TABLE1_HIDDEN = "hidden";

    public static final String[] ALL_KEYS_TABLE1 = new String[]
            {KEY_ROWID, KEY_TABLE1_CARNAME, KEY_TABLE1_CARMAKE,
            KEY_TABLE1_CARMODEL, KEY_TABLE1_HIGHWAY, KEY_TABLE1_CITY,
            KEY_TABLE1_YEAR, KEY_TABLE1_TRANSMISSION, KEY_TABLE1_LITERENGINE,
            KEY_TABLE1_GASTYPE, KEY_TABLE1_HIDDEN};

    public static final int COL_TABLE1_NAME = 1;
    public static final int COL_TABLE1_MAKE = 2;
    public static final int COL_TABLE1_MODEL = 3;
    public static final int COL_TABLE1_HIGHWAY = 4;
    public static final int COL_TABLE1_CITY = 5;
    public static final int COL_TABLE1_YEAR = 6;
    public static final int COL_TABLE1_TRANSMISSION = 7;
    public static final int COL_TABLE1_LITERENGINE = 8;
    public static final int COL_TABLE1_GASTYPE = 9;
    public static final int COL_TABLE1_HIDDEN = 10;

    //JOURNEYS
    public static final String KEY_TABLE2_JOURNEYNAME = "journeyName";
    public static final String KEY_TABLE2_CARNAME = "carName";
    public static final String KEY_TABLE2_ROUTENAME = "routeName";
    public static final String KEY_TABLE2_HIDDEN = "hidden";

    public static final String[] ALL_KEYS_TABLE2 = new String[]
            {KEY_ROWID, KEY_TABLE2_JOURNEYNAME,
            KEY_TABLE2_ROUTENAME, KEY_TABLE2_CARNAME, KEY_TABLE2_HIDDEN};

    public static final int COL_TABLE2_JOURNEYNAME = 1;
    public static final int COL_TABLE2_CARNAME = 2;
    public static final int COL_TABLE2_ROUTENAME = 3;
    public static final int COL_TABLE2_HIDDEN = 4;

    //ROUTES
    public static final String KEY_TABLE3_ROUTENAME = "routeName";
    public static final String KEY_TABLE3_CITYKM = "cityKM";
    public static final String KEY_TABLE3_HIGHWAYKM = "highwayKM";
    public static final String KEY_TABLE3_HIDDEN = "hidden";

    public static final String[] ALL_KEYS_TABLE3 = new String[]
            {KEY_ROWID, KEY_TABLE3_ROUTENAME,
            KEY_TABLE3_HIGHWAYKM, KEY_TABLE3_CITYKM, KEY_TABLE3_HIDDEN};

    public static final int COL_TABLE3_ROUTENAME = 1;
    public static final int COL_TABLE3_CITYKM = 2;
    public static final int COL_TABLE3_HIGHWAYKM = 3;
    public static final int COL_TABLE3_HIDDEN = 4;

    //UTILITIES
    public static final String KEY_TABLE4_UTILITYNAME = "routeName";
    public static final String KEY_TABLE4_GASTYPE = "cityKM";
    public static final String KEY_TABLE4_AMOUNT = "highwayKM";
    public static final String KEY_TABLE4_EMISSIONS = "emissions";
    public static final String KEY_TABLE4_NUMBEROFPEOPLE = "numberOfPeople";
    public static final String KEY_TABLE4_STARTDATE = "startDate";
    public static final String KEY_TABLE4_ENDDATE = "endDate";
    public static final String KEY_TABLE4_HIDDEN = "hidden";

    public static final String[] ALL_KEYS_TABLE4 = new String[]
            {KEY_ROWID, KEY_TABLE4_UTILITYNAME, KEY_TABLE4_GASTYPE,
            KEY_TABLE4_AMOUNT, KEY_TABLE4_EMISSIONS, KEY_TABLE4_NUMBEROFPEOPLE,
            KEY_TABLE4_STARTDATE, KEY_TABLE4_ENDDATE, KEY_TABLE4_HIDDEN};

    public static final int COL_TABLE4_UTILITYNAME = 1;
    public static final int COL_TABLE4_GASTYPE = 2;
    public static final int COL_TABLE4_AMOUNT = 3;
    public static final int COL_TABLE4_EMISSIONS = 4;
    public static final int COL_TABLE4_NUMBEROFPEOPLE = 5;
    public static final int COL_TABLE4_STARTDATE = 6;
    public static final int COL_TABLE4_ENDDATE = 7;
    public static final int COL_TABLE4_HIDDEN = 8;

    // DB info: it's name, and the tables we are using.
    public static final String DATABASE_NAME = "userDB";
    public static final String DATABASE_TABLE1 = "userCars";
    public static final String DATABASE_TABLE2 = "userJourneys";
    public static final String DATABASE_TABLE3 = "userRoutes";
    public static final String DATABASE_TABLE4 = "userUtilities";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_SQL_TABLE1 =
            "create table " + DATABASE_TABLE1
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
			/*
			 * CHANGE 2:
			 */
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_TABLE1_CARNAME + " text not null, "
                    + KEY_TABLE1_CARMAKE + " text not null, "
                    + KEY_TABLE1_CARMODEL + " text not null,"
                    + KEY_TABLE1_HIGHWAY + " real not null,"
                    + KEY_TABLE1_CITY + " real not null,"
                    + KEY_TABLE1_YEAR + " integer not null,"
                    + KEY_TABLE1_TRANSMISSION + " text not null,"
                    + KEY_TABLE1_LITERENGINE + " real not null,"
                    + KEY_TABLE1_GASTYPE + " text not null,"
                    + KEY_TABLE1_HIDDEN + " integer not null"
                    // Rest  of creation:
                    + ");";

    private static final String DATABASE_CREATE_SQL_TABLE2 =
            "create table " + DATABASE_TABLE2
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
			/*
			 * CHANGE 2:
			 */
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_TABLE2_JOURNEYNAME + " text not null, "
                    + KEY_TABLE2_CARNAME + " text not null, "
                    + KEY_TABLE2_ROUTENAME + " text not null,"
                    + KEY_TABLE2_HIDDEN + " integer not null"
                    // Rest  of creation:
                    + ");";

    private static final String DATABASE_CREATE_SQL_TABLE3 =
            "create table " + DATABASE_TABLE3
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
			/*
			 * CHANGE 2:
			 */
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_TABLE3_ROUTENAME+ " text not null, "
                    + KEY_TABLE3_HIGHWAYKM + " real not null, "
                    + KEY_TABLE3_CITYKM + " real not null,"
                    + KEY_TABLE3_HIDDEN + " integer not null"
                    // Rest  of creation:
                    + ");";

    private static final String DATABASE_CREATE_SQL_TABLE4 =
            "create table " + DATABASE_TABLE4
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
			/*
			 * CHANGE 2:
			 */
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_TABLE4_UTILITYNAME+ " text not null, "
                    + KEY_TABLE4_GASTYPE + " text not null, "
                    + KEY_TABLE4_AMOUNT + " real not null,"
                    + KEY_TABLE4_EMISSIONS + " real not null"
                    + KEY_TABLE4_NUMBEROFPEOPLE + " integer not null"
                    + KEY_TABLE4_STARTDATE + " text not null"
                    + KEY_TABLE4_ENDDATE + " text not null"
                    + KEY_TABLE4_HIDDEN + " integer not null"
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
    public long insertUserCarsRow(String carName, String carMake, String carModel,
            double highwayKM, double cityKM, int year,
            String transmission, double literEngine, String gasType,
            int hidden) {
		/*
		 * CHANGE 3:
		 */
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TABLE1_CARNAME, carName);
        initialValues.put(KEY_TABLE1_CARMAKE, carMake);
        initialValues.put(KEY_TABLE1_CARMODEL, carModel);
        initialValues.put(KEY_TABLE1_HIGHWAY, highwayKM);
        initialValues.put(KEY_TABLE1_CITY, cityKM);
        initialValues.put(KEY_TABLE1_YEAR, year);
        initialValues.put(KEY_TABLE1_TRANSMISSION, transmission);
        initialValues.put(KEY_TABLE1_LITERENGINE, literEngine);
        initialValues.put(KEY_TABLE1_GASTYPE, gasType);
        initialValues.put(KEY_TABLE1_HIDDEN, hidden);
        // Insert it into the database.
        return db.insert(DATABASE_TABLE1, null, initialValues);
    }

    public long insertUserJourneysRow(String journeyName, String carName, String routeName,
                                int hidden) {
		/*
		 * CHANGE 3:
		 */
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TABLE2_JOURNEYNAME, journeyName);
        initialValues.put(KEY_TABLE2_CARNAME, carName);
        initialValues.put(KEY_TABLE2_ROUTENAME, routeName);
        initialValues.put(KEY_TABLE2_HIDDEN, hidden);
        // Insert it into the database.
        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    public long insertUserRoutesRow(String routeName, double highwayKM, double cityKM,
                                int hidden) {
		/*
		 * CHANGE 3:
		 */
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TABLE3_ROUTENAME, routeName);
        initialValues.put(KEY_TABLE3_HIGHWAYKM, highwayKM);
        initialValues.put(KEY_TABLE3_CITYKM, cityKM);
        initialValues.put(KEY_TABLE3_HIDDEN, hidden);
        // Insert it into the database.
        return db.insert(DATABASE_TABLE3, null, initialValues);
    }

    public long insertUserUtilitiesRow(String utilityName, String gasType, double amount,
                                       double emissions, int numberOfPeople, String startDate,
                                       String endDate, int hidden) {
		/*
		 * CHANGE 3:
		 */
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TABLE4_UTILITYNAME, utilityName);
        initialValues.put(KEY_TABLE4_GASTYPE, gasType);
        initialValues.put(KEY_TABLE4_AMOUNT, amount);
        initialValues.put(KEY_TABLE4_EMISSIONS, emissions);
        initialValues.put(KEY_TABLE4_NUMBEROFPEOPLE, numberOfPeople);
        initialValues.put(KEY_TABLE4_STARTDATE, startDate);
        initialValues.put(KEY_TABLE4_ENDDATE, endDate);
        initialValues.put(KEY_TABLE3_HIDDEN, hidden);
        // Insert it into the database.
        return db.insert(DATABASE_TABLE4, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteUserCarRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE1, where, null) != 0;
    }

    public boolean deleteUserJourneyRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE2, where, null) != 0;
    }

    public boolean deleteUserRouteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE3, where, null) != 0;
    }

    public boolean deleteUserUtilityRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE4, where, null) != 0;
    }

    public void deleteAllUserData() {
        Cursor c = getAllUserCarRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteUserCarRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c = getAllUserJourneysRows();
        rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteUserJourneyRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c = getAllUserRouteRows();
        rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteUserRouteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c = getAllUserUtilityRows();
        rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteUserUtilityRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllUserCarRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE1, ALL_KEYS_TABLE1,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Return all data in the database.
    public Cursor getAllUserJourneysRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE2, ALL_KEYS_TABLE2,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Return all data in the database.
    public Cursor getAllUserRouteRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE3, ALL_KEYS_TABLE3,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Return all data in the database.
    public Cursor getAllUserUtilityRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE4, ALL_KEYS_TABLE4,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getUserCarsRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE1, ALL_KEYS_TABLE1,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getUserJourneysRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE2, ALL_KEYS_TABLE2,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getUserRoutesRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE3, ALL_KEYS_TABLE3,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getUserUtilitiesRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE4, ALL_KEYS_TABLE4,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateUserCarRow(long rowId, String carName, String carMake, String carModel,
                                    double highwayKM, double cityKM, int year,
                                    String transmission, double literEngine, String gasType,
                                    int hidden) {
        String where = KEY_ROWID + "=" + rowId;
		/*
		 * CHANGE 4:
		 */
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TABLE1_CARNAME, carName);
        newValues.put(KEY_TABLE1_CARMAKE, carMake);
        newValues.put(KEY_TABLE1_CARMODEL, carModel);
        newValues.put(KEY_TABLE1_HIGHWAY, highwayKM);
        newValues.put(KEY_TABLE1_CITY, cityKM);
        newValues.put(KEY_TABLE1_YEAR, year);
        newValues.put(KEY_TABLE1_TRANSMISSION, transmission);
        newValues.put(KEY_TABLE1_LITERENGINE, literEngine);
        newValues.put(KEY_TABLE1_GASTYPE, gasType);
        newValues.put(KEY_TABLE1_HIDDEN, hidden);

        // Insert it into the database.
        return db.update(DATABASE_TABLE1, newValues, where, null) != 0;
    }

    public boolean updateUserJourneyRow(long rowId, String journeyName, String carName, String routeName,
                                        int hidden) {
        String where = KEY_ROWID + "=" + rowId;
		/*
		 * CHANGE 4:
		 */
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TABLE2_JOURNEYNAME, journeyName);
        newValues.put(KEY_TABLE2_CARNAME, carName);
        newValues.put(KEY_TABLE2_ROUTENAME, routeName);
        newValues.put(KEY_TABLE2_HIDDEN, hidden);

        // Insert it into the database.
        return db.update(DATABASE_TABLE2, newValues, where, null) != 0;
    }

    public boolean updateUserRouteRow(long rowId, String routeName, double highwayKM, double cityKM,
                                      int hidden) {
        String where = KEY_ROWID + "=" + rowId;
		/*
		 * CHANGE 4:
		 */
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TABLE3_ROUTENAME, routeName);
        newValues.put(KEY_TABLE3_HIGHWAYKM, highwayKM);
        newValues.put(KEY_TABLE3_CITYKM, cityKM);
        newValues.put(KEY_TABLE3_HIDDEN, hidden);

        // Insert it into the database.
        return db.update(DATABASE_TABLE3, newValues, where, null) != 0;
    }

    public boolean updateUserUtilityRow(long rowId, String utilityName, String gasType,
                                        double amount, double emissions, int numberOfPeople,
                                        String startDate, String endDate, int hidden) {
        String where = KEY_ROWID + "=" + rowId;
		/*
		 * CHANGE 4:
		 */
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TABLE4_UTILITYNAME, utilityName);
        newValues.put(KEY_TABLE4_GASTYPE, gasType);
        newValues.put(KEY_TABLE4_AMOUNT, amount);
        newValues.put(KEY_TABLE4_EMISSIONS, emissions);
        newValues.put(KEY_TABLE4_NUMBEROFPEOPLE, numberOfPeople);
        newValues.put(KEY_TABLE4_STARTDATE, startDate);
        newValues.put(KEY_TABLE4_ENDDATE, endDate);
        newValues.put(KEY_TABLE4_HIDDEN, hidden);

        // Insert it into the database.
        return db.update(DATABASE_TABLE3, newValues, where, null) != 0;
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
            _db.execSQL(DATABASE_CREATE_SQL_TABLE1);
            _db.execSQL(DATABASE_CREATE_SQL_TABLE2);
            _db.execSQL(DATABASE_CREATE_SQL_TABLE3);
            _db.execSQL(DATABASE_CREATE_SQL_TABLE4);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");
            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE1);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE4);
            // Recreate new database:
            onCreate(_db);
        }
    }
}