package com.apps.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;

public class CalendarUtil {
	public static String getDateDisplay(Calendar cal){
		return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)+" " + cal.get(Calendar.DATE) + ", " + cal.get(Calendar.YEAR);
	}
	
	public static String getShortDateDisplay(Calendar cal){
		return cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)+" " + cal.get(Calendar.DATE);
	}
	
	public static String getShortMonthYearDisplay(Calendar cal){
		return cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)+" " + cal.get(Calendar.YEAR);
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

    public static String getShortDuration(Calendar startDate, Calendar endDate)
    {
        long milliseconds = endDate.getTimeInMillis() - startDate.getTimeInMillis();

        int j = (int) ((milliseconds / (1000 * 60)) % 60);
        int k = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        if (j == 1) {
            return j + " hr";
        }
        if (j < 1) {
            return k + " min";
        }
        return j + " hrs ";

    }
	
	public static Bundle bundleCalendar(Calendar cal){
		Bundle args = new Bundle();
		if (cal == null)
			cal = Calendar.getInstance(Locale.getDefault());;
	    args.putInt("year", cal.get(Calendar.YEAR));
	    args.putInt("month", cal.get(Calendar.MONTH));
	    args.putInt("day", cal.get(Calendar.DAY_OF_MONTH));
	    args.putInt("hour", cal.get(Calendar.HOUR_OF_DAY));
	    args.putInt("minute", cal.get(Calendar.MINUTE));
	    return args;
	}
	
	public static Bundle bundleCalendar(Calendar cal, long minDate){
		Bundle args = new Bundle();
		if (cal == null)
			cal = Calendar.getInstance(Locale.getDefault());;
	    args.putInt("year", cal.get(Calendar.YEAR));
	    args.putInt("month", cal.get(Calendar.MONTH));
	    args.putInt("day", cal.get(Calendar.DAY_OF_MONTH));
	    args.putInt("hour", cal.get(Calendar.HOUR_OF_DAY));
	    args.putInt("minute", cal.get(Calendar.MINUTE));
	    args.putLong("minDate", minDate);
	    return args;
	}
}
