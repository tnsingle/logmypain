package com.apps.logmypain_dev;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.apps.logmypain.R.layout;
import com.apps.utils.DatabaseHelper;
import com.apps.utils.ViewRecordsAdapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;
 
public class MonthSelectedListener implements OnItemSelectedListener {
//	private int year;
//	private int month;
//	
	private ListView list;
	private int year;
	public MonthSelectedListener(ListView list, String year){
		this.list = list;
		this.year = getYear(year);
	}
//	
//	public YearSelectedListener(String year){
//		this.year = getYear(year);
//	}
//	
//	public YearSelectedListener(String year, String month){
//		this.year = getYear(year);
//		this.month = getMonth(month);
//	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		DatabaseHelper db = new DatabaseHelper(parent.getContext());
		int month = getMonth(parent.getItemAtPosition(pos).toString());
		ViewRecordsAdapter rAdapter;
		if(month > -1){
			rAdapter = new ViewRecordsAdapter(parent.getContext(),
	                layout.activity_view_records, db.getAllRecordsByMonth(month+1, year, "desc"));
		}else{
			rAdapter = new ViewRecordsAdapter(parent.getContext(),
	                layout.activity_view_records, db.getAllRecordsByYear(year));
		}
		
		rAdapter.notifyDataSetChanged();
		list.setAdapter(rAdapter);
	}
 
  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
  }
  
  private int getYear(String year){
	  Calendar c = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
  	SimpleDateFormat sdf = new SimpleDateFormat("yyyy",Locale.getDefault());
  	if(year != ""){
		try {
			java.util.Date dt;
			dt = sdf.parse(year);
			c.setTime(dt);
			//return c.get(Calendar.YEAR);
          
          
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	}
		
		return c.get(Calendar.YEAR);
  }
  
  private int getMonth(String month){
	  Date date;
	  if(month != ""){
	try {
		date = new SimpleDateFormat("MMM", Locale.getDefault()).parse(month);
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    System.out.println("month: " + month);
	    System.out.println("month #: " + cal.get(Calendar.MONTH));
	    return cal.get(Calendar.MONTH);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  }
	
	return -1;
	    
  }
 
}