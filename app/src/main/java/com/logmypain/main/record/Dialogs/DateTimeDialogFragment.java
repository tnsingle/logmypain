package com.logmypain.main.record.Dialogs;

import java.util.Calendar;

import com.logmypain.main.R;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DateTimeDialogFragment extends DialogFragment {
	
	 public interface DateTimeDialogListener {
	        public void onReturnVal(DatePicker date, TimePicker time);
	    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    // Get the layout inflater
	    final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date_time, null);
	    final DatePicker date = (DatePicker)view.findViewById(R.id.datePicker);
	    final TimePicker time = (TimePicker)view.findViewById(R.id.timePicker);
	    Calendar cal = Calendar.getInstance();
	    
	    //date.setMaxDate(cal.getTimeInMillis());
	    //long minDate = getArguments().getLong("minDate");
	    //if (minDate != 0L){
	    //	date.setMinDate(minDate);
	    //}
	    date.updateDate(getArguments().getInt("year"),getArguments().getInt("month"),getArguments().getInt("day"));
	    time.setCurrentHour(getArguments().getInt("hour"));
	    time.setCurrentMinute(getArguments().getInt("minute"));
	    
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view);
	    
	    
	    
	    // Add action buttons
	    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   DateTimeDialogListener activity = (DateTimeDialogListener) getActivity();
	            	    activity.onReturnVal(date, time);
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   DateTimeDialogFragment.this.getDialog().cancel();
	               }
	           });
	    return builder.create();
	}
	
	

}
