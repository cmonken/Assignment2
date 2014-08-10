package com.group1.assignment2;

/* Some code modified from example at http://hmkcode.com/android-simple-sqlite-database-tutorial/ */

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // database name
    public static final String DATABASE_NAME = "EventData";
    // database version
    public static final int DATABASE_VERSION = 1;

    // Table Name
    public static String TABLE_PATIENT = "";

    // Patients Table Column names
    public static final String KEY_ID = "id";
    public static final String COLUMN_TIME = "timestamp";
    public static final String COLUMN_X = "x";
    public static final String COLUMN_Y = "y";
    public static final String COLUMN_Z = "z";

    // Table creation statement
    String CREATE_PATIENT_TABLE = "CREATE TABLE " + TABLE_PATIENT + " ( " + KEY_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_TIME + " TEXT, " + COLUMN_X + " INTEGER, " + COLUMN_Y + " INTEGER, " + COLUMN_Z + " INTEGER );";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create patients table
        db.execSQL(CREATE_PATIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older patients table if existed
        db.execSQL("DROP TABLE IF EXISTS patients");

        // create fresh patients table
        this.onCreate(db);
    }

    public void setTableName (String tableName){
        TABLE_PATIENT = tableName;
    }

    public void createPatientTable(SQLiteDatabase db, String tableName) {

//        setTableName(tableName);

        db.execSQL(CREATE_PATIENT_TABLE);
    }

}