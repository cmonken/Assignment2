package com.group1.assignment2;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;

public class DatabaseHandler {

    private SQLiteDatabase db;
    private MySQLiteHelper myHelper;
    private String[] allColumns = { myHelper.KEY_ID, myHelper.COLUMN_TIME, myHelper.COLUMN_X,
            myHelper.COLUMN_Y, myHelper.COLUMN_Z };

    public DatabaseHandler(Context context){
        myHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException{
        db = myHelper.getWritableDatabase();
    }

    public void close() {
        myHelper.close();
    }

    /**
     * Patient table CRUD operations
     */
    public void insertEventData(ObjectEventData objectEventData){
        ContentValues values = new ContentValues();
        values.put(myHelper.KEY_ID, objectEventData.getId());
        values.put(myHelper.COLUMN_TIME, objectEventData.getTimeStamp());
        values.put(myHelper.COLUMN_X, objectEventData.getX());
        values.put(myHelper.COLUMN_Y, objectEventData.getY());
        values.put(myHelper.COLUMN_Z, objectEventData.getZ());


        long insertId = db.insert(myHelper.TABLE_PATIENT, null, values);
        String args = myHelper.KEY_ID + " = " + insertId;
    }

}