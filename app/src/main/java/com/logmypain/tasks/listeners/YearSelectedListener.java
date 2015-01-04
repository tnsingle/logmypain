package com.logmypain.tasks.listeners;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.logmypain.R.layout;
import com.logmypain.utils.DatabaseHelper;
import com.logmypain.tasks.adapters.ViewRecordsAdapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class YearSelectedListener implements OnItemSelectedListener {
	private ListView list;
	private Change change;
	Spinner pairedSpinner;
	public YearSelectedListener(ListView list, Spinner pairedSpinner, Change change){
		this.list = list;
		this.change = change;
		this.pairedSpinner = pairedSpinner;
//		if(change == Change.MONTH){
//			this.year = getYear(pairedSpinner.getSelectedItem().toString());
//			this.month = getMonth("");
//		}else{
//			this.month = getMonth(pairedSpinner.getSelectedItem().toString());
//			this.year = getYear("");
//		}
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
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		ViewRecordsAdapter rAdapter;
		DatabaseHelper db = new DatabaseHelper(parent.getContext());
		int year;
		int month = -1;
		if (change == Change.MONTH){
			month = getMonth(parent.getItemAtPosition(pos).toString());
			year = getYear(pairedSpinner.getSelectedItem().toString());
		}else{
			year = getYear(parent.getItemAtPosition(pos).toString());
			month = getMonth(pairedSpinner.getSelectedItem().toString());
		}
		if(month > -1){
			rAdapter = new ViewRecordsAdapter(parent.getContext(),
	                layout.activity_view_records, db.getAllRecordsByMonth(month+1, year));
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
  	if(year != null && year != ""){
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
	  if(month != null && month != ""){
	try {
		date = new SimpleDateFormat("MMM", Locale.getDefault()).parse(month);
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal.get(Calendar.MONTH);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  }
	
	return -1;
	    
  }
  
  public static enum Change{
	  YEAR,
	  MONTH
  }
 
}