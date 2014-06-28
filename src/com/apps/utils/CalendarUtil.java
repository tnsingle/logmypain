package com.apps.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtil {
	public static String getDateDisplay(Calendar cal){
		return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)+" " + cal.get(Calendar.DATE) + ", " + cal.get(Calendar.YEAR);
	}
	
	public static String getShortDateDisplay(Calendar cal){
		return cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)+" " + cal.get(Calendar.DATE);
	}
	
	public static String getTimeDisplay(Calendar cal){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
		return dateFormat.format(cal.getTime());
	} 
	
	public static String getDuration(Calendar start, Calendar end){
		Date startDate = start.getTime();
		Date endDate = end.getTime();

		long difference = endDate.getTime() - startDate.getTime(); 
		int days = (int) (difference / (1000*60*60*24));  
		int hours = (int) (difference / (1000*60*60)); //(1000*60*60*24*days)) / (1000*60*60)); 
		int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
		
		return hours + " hours and " + min + " minutes";
	}
}
