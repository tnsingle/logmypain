package com.apps.headache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.apps.utils.CalendarGridViewAdapter;
import com.apps.utils.CalendarUtil;
import com.apps.utils.CalendarViewDay;
import com.apps.utils.DatabaseHelper;
import com.apps.utils.HeadacheRecord;
import com.apps.utils.Models.Headache;
import com.apps.utils.ViewRecordsAdapter;
import com.apps.headache.R;
import com.apps.headache.R.layout;
import com.apps.headache.record.ModifyRecordActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class ViewCalendarActivity extends Activity {
	
	private Calendar calendar;
	private int year;
	private int month;
	private int startDayOfWeek;
	private int daysInMonth;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("View_Calendar");
		if(bundle == null){
			this.calendar = Calendar.getInstance(Locale.getDefault());
		}else{
			this.calendar = new GregorianCalendar(bundle.getInt("year"), bundle.getInt("month"), bundle.getInt("day"));
		}
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		startDayOfWeek = (new GregorianCalendar(year, month, 1)).get(Calendar.DAY_OF_WEEK);
		daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		setUpCalendarView();
		
	}

	private void setUpCalendarView(){
		setCalendarTitle();
		
		CalendarGridViewAdapter rAdapter;
		ArrayList<CalendarViewDay> days;
		
		
		List<HeadacheRecord> headaches = getHeadacheDays();
		CalendarViewDay[] calendarDays = getCalendarDays(headaches);
		
		days = new ArrayList<CalendarViewDay>(Arrays.asList(calendarDays));
		rAdapter = new CalendarGridViewAdapter(ViewCalendarActivity.this,
                layout.calendar_cell, days);
		GridView calendarView = (GridView) this.findViewById(R.id.calendarGrid);
		calendarView.setAdapter(rAdapter);
	}
	
	private void setCalendarTitle(){
		TextView currentMonthText = (TextView) findViewById(R.id.calendarMonth);
		TextView currentYearText = (TextView) findViewById(R.id.calendarYear);
		
		currentMonthText.setText(this.calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()));
		currentYearText.setText(Integer.toString(this.year));
	}
	
	private List<HeadacheRecord> getHeadacheDays(){
		DatabaseHelper db = new DatabaseHelper(this);
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
		Intent intent = this.getIntent();
		Calendar newCal = calendar;
		newCal.set(Calendar.MONTH, month);
		intent.putExtra("View_Calendar", CalendarUtil.bundleCalendar(newCal));
		finish();
	    startActivity(intent);
	}
}
