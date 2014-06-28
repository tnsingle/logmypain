package com.apps.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "headache.db";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_TABLE_NAME = "headache_records";
    private static final String DATABASE_TABLE_CREATE =
                "CREATE TABLE " + DATABASE_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                		"Start_Date_Time DATETIME, " +
                		"End_Date_Time DATETIME, " +
                		"Intensity INTEGER, " +
                		"Triggers VARCHAR(160), " +
                		"Meds VARCHAR(160));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_TABLE_CREATE);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int newVersion, int oldVersion) {
		// TODO Auto-generated method stub
		if (oldVersion < 2) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
	        onCreate(db);
        }
	}
	
	// Add new record
    public long addRecord(HeadacheRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        
 
        // Inserting Row
        long id = db.insertOrThrow(DATABASE_TABLE_NAME, null, setupValues(record));
        //record.setId(id);
        db.close(); // Closing database connection
        return id;
    }
    
    public void updateRecord(HeadacheRecord record){
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	 db.update(DATABASE_TABLE_NAME, setupValues(record), "ID=" + record.getId(), null);
    }
    
    public void deleteRecord(long id){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(DATABASE_TABLE_NAME, "ID=" + id, null);
    	//db.delete("sqlite_sequence", "name = "+DATABASE_TABLE_NAME, null);
    }
    
    public List<HeadacheRecord> getAllRecords(){
    	List<HeadacheRecord> recordList = new ArrayList<HeadacheRecord>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_NAME + " order by Start_Date_Time DESC";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	recordList.add(getRecord(cursor));
            } while (cursor.moveToNext());
        }
        //System.out.println("item count in DatabaseHelper: " + recordList.size());
        // return contact list
        return recordList;
    }
    
    public HeadacheRecord getRecord(long id){
    	String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_NAME + " where ID = " + id;
    	SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
        	return getRecord(cursor);
        }else
        	return new HeadacheRecord();
    }
    
    private HeadacheRecord getRecord(Cursor cursor){
    	HeadacheRecord record = new HeadacheRecord();
    	Calendar t = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.getDefault());
    	
		try {
			java.util.Date dt;
			java.util.Date enddt;
			dt = sdf.parse(cursor.getString(1));
        	t.setTime(dt);
            record.setStart(dt);
            
            if(cursor.getString(2) != null){
            enddt = sdf.parse(cursor.getString(2));
            t.setTime(enddt);
            record.setEnd(enddt);
            
            //System.out.println("start: " + dt.toString() + "\nend:" + enddt.toString());
            }
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.setIntensity(cursor.getInt(3));
        return record;
    }
    
    private ContentValues setupValues(HeadacheRecord record){
    	ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()); 
        Calendar startDateTime = record.getStart();
        Calendar endDateTime = record.getEnd();
        
        if (startDateTime != null)
        	values.put("Start_Date_Time", dateFormat.format(startDateTime.getTime()));
        if (endDateTime != null)
        	values.put("End_Date_Time", dateFormat.format(endDateTime.getTime()));
        values.put("Intensity", record.getIntensity());
        
        return values;
    }
}
