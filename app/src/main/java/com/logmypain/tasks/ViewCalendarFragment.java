package com.logmypain.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.logmypain.utils.CalendarGridViewAdapter;
import com.logmypain.utils.CalendarViewDay;
import com.logmypain.utils.DatabaseHelper;
import com.logmypain.utils.HeadacheRecord;
import com.logmypain.main.R;
import com.logmypain.main.R.layout;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

public class ViewCalendarFragment extends Fragment {
	
	private View view;
	private Calendar calendar;
	private int year;
	private int month;
	private int startDayOfWeek;
	private int daysInMonth;
	private CalendarGridViewAdapter rAdapter;
	private GridView calendarView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
            Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = inflater.inflate(R.layout.calendar_view, container, false);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getBundleExtra("View_Calendar");
		if(bundle == null){
			this.calendar = Calendar.getInstance(Locale.getDefault());
		}else{
			this.calendar = new GregorianCalendar(bundle.getInt("year"), bundle.getInt("month"), bundle.getInt("day"));
		}
		
		
		setupCalendarData();
		calendarView = (GridView) view.findViewById(R.id.calendarGrid);
		calendarView.setAdapter(rAdapter);
		return view;
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.calendar_view, menu);
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//	    // Handle presses on the action bar items
//	    switch (item.getItemId()) {
//	        case R.id.action_view_as_list:
//	        	Intent intent = new Intent(this, ViewRecordsActivity.class);	    		
//	    	    startActivity(intent);
//	            return true;
//	        default:
//	            return super.onOptionsItemSelected(item);
//	    }
//	}
	private void setupCalendarData(){
		
		setCalendarData();
		setCalendarTitle();
		
		ArrayList<CalendarViewDay> days;
		
		
		List<HeadacheRecord> headaches = getHeadacheDays();
		CalendarViewDay[] calendarDays = getCalendarDays(headaches);
		
		days = new ArrayList<CalendarViewDay>(Arrays.asList(calendarDays));
		rAdapter = new CalendarGridViewAdapter(this.getActivity(),
                layout.calendar_cell, days);
	}
	
	private void setCalendarData(){
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		startDayOfWeek = (new GregorianCalendar(year, month, 1)).get(Calendar.DAY_OF_WEEK);
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	private void setCalendarTitle(){
		TextView currentMonthText = (TextView) view.findViewById(R.id.calendarMonth);
		TextView currentYearText = (TextView) view.findViewById(R.id.calendarYear);
		
		currentMonthText.setText(this.calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
		currentYearText.setText(Integer.toString(this.year));
	}
	
	private List<HeadacheRecord> getHeadacheDays(){
		DatabaseHelper db = new DatabaseHelper(this.getActivity());
		return db.getAllRecordsByMonth(this.month+1, this.year);
	}
	
	private CalendarViewDay[] getCalendarDays(List<HeadacheRecord> headaches){
		CalendarViewDay[] daysArray = new CalendarViewDay[42];
		for(int i = 0; i < headaches.size(); i++){
			HeadacheRecord headache = headaches.get(i);
			int dayOfMonth = headache.getStart().get(Calendar.DAY_OF_MONTH);
			CalendarViewDay day = new CalendarViewDay(dayOfMonth, headache);
			daysArray[this.startDayOfWeek - 1 + dayOfMonth-1] = day;
		}

		int count = 1;
		for(int k = this.startDayOfWeek-1; k < this.daysInMonth+this.startDayOfWeek-1; k++){
			if(daysArray[k] == null){
				daysArray[k] = new CalendarViewDay(count);
			}
			
			count++;
		}
		
		return daysArray;
	}

	public void viewPrevMonth(View view){
		changeMonth(this.month-1);
	}
	
	public void viewNextMonth(View view){
		changeMonth(this.month+1);
	}
	
	private void changeMonth(int month){
		this.month = month;
		calendar.set(Calendar.MONTH, this.month);
		if(this.month > calendar.getMaximum(Calendar.MONTH)){
			this.month = calendar.getMinimum(Calendar.MONTH);
		}else if(this.month < calendar.getMinimum(Calendar.MONTH)){
			this.month = calendar.getMaximum(Calendar.MONTH);
		}
		
		setupCalendarData();
		this.rAdapter.notifyDataSetChanged();
		this.calendarView.setAdapter(rAdapter);
	}
}
