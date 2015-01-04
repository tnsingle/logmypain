package com.logmypain.utils;

import java.text.DecimalFormat;
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

import com.logmypain.utils.Models.HeadacheRecord;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "headache.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_TABLE_NAME = "headache_records";
    private static final String DATABASE_TABLE_CREATE =
                "CREATE TABLE " + DATABASE_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                		"Start_Date_Time DATETIME, " +
                		"End_Date_Time DATETIME, " +
                		"Intensity INTEGER," +
                        "Notes VARCHAR(255));";

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
		//if (oldVersion < 2) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
	        onCreate(db);
        //}
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
    
    public List<HeadacheRecord> getAllRecordsByMonth(int month, int year){
    	List<HeadacheRecord> recordList = new ArrayList<HeadacheRecord>();
    	DecimalFormat formatter = new DecimalFormat("00");
    	String monthFormatted = formatter.format(month);
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_NAME + " where strftime('%m',Start_Date_Time)='"+monthFormatted+"' AND strftime('%Y',Start_Date_Time) = '"+year+"' order by Start_Date_Time DESC";
 
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

    public List<HeadacheRecord> getAllRecordsByYear(int year){
        List<HeadacheRecord> recordList = new ArrayList<HeadacheRecord>();
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE_NAME + " where strftime('%Y',Start_Date_Time) = '"+year+"' order by Start_Date_Time DESC";

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
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00",Locale.getDefault());
    	
		try {
			java.util.Date dt;
			java.util.Date enddt;

            String startStr = cursor.getString(1);
            String endStr = cursor.getString(2);

			dt = sdf.parse(startStr);
            record.setStart(dt);
            
            if(endStr != null){
            enddt = sdf.parse(endStr);
            record.setEnd(enddt);
            
            //System.out.println("start: " + dt.toString() + "\nend:" + enddt.toString());
            }
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        record.setId(Integer.parseInt(cursor.getString(0)));
        record.setIntensity(cursor.getInt(3));
        record.setNotes(cursor.getString(4));
        return record;
    }

    public List<String> getYears()
    {
        ArrayList localArrayList = new ArrayList();
        Cursor localCursor = getWritableDatabase().rawQuery("SELECT DISTINCT strftime('%Y',Start_Date_Time) FROM " + DATABASE_TABLE_NAME + " ORDER BY Start_Date_Time DESC", null);
        if (localCursor.moveToFirst()) {
            do
            {
                localArrayList.add(localCursor.getString(0));
            } while (localCursor.moveToNext());
        }
        return localArrayList;
    }
    
    private ContentValues setupValues(HeadacheRecord record){
    	ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00", Locale.getDefault());
        Calendar startDateTime = record.getStart();
        Calendar endDateTime = record.getEnd();
        
        if (startDateTime != null)
        	values.put("Start_Date_Time", dateFormat.format(startDateTime.getTime()));
        if (endDateTime != null)
        	values.put("End_Date_Time", dateFormat.format(endDateTime.getTime()));
        values.put("Intensity", record.getIntensity());
        values.put("Notes", record.getNotes());
        return values;
    }
}
