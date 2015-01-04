package com.logmypain.tasks.adapters;

import java.util.List;

import com.logmypain.R;
import com.logmypain.utils.CalendarViewDay;
import com.logmypain.utils.Models.HeadacheRecord;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CalendarGridViewAdapter extends ArrayAdapter <CalendarViewDay>{

	private Context context; 
    private int layoutResourceId;    
    private List<CalendarViewDay> days;
    private int numDays;
    
    public CalendarGridViewAdapter(Context context, int layoutResourceId, List<CalendarViewDay> days) {
        super(context, layoutResourceId, days);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.days = days;
    }
    
    @SuppressLint("NewApi")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){ // no view to re-use, create new
        	LayoutInflater inflater = (LayoutInflater)context.getSystemService
        		      (Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.calendar_cell, null);
        }
        TextView calendarDay = (TextView) view.findViewById(R.id.calendar_day);
        CalendarViewDay thisDay = days.get(position);
        if(thisDay != null){
        	calendarDay.setText(Integer.toString(thisDay.getDay()));
        	HeadacheRecord record = thisDay.getRecord();
        	if(record != null){
        		//calendarDay.setBackgroundColor(Color.parseColor("#ff007f"));
        		GradientDrawable marker = (GradientDrawable) calendarDay.getBackground().mutate();
        		int color;
        		if(record.getIntensity() <= 3){
        			color = context.getResources().getColor(R.color.intensity_mild);
        		}else if (record.getIntensity() <= 6){
        			color = context.getResources().getColor(R.color.intensity_mod);
        		}else{
        			color = context.getResources().getColor(R.color.intensity_sev);
        		}
        		marker.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        		//marker.mutate()).setColorFilter(Color.parseColor("#ff007f"), PorterDuff.Mode.SRC_ATOP);
        	}
        }
        
//        final HeadacheRecord record = recordList.get(position);
//        if (view == null){ // no view to re-use, create new
//        	LayoutInflater inflater = (LayoutInflater)context.getSystemService
//        		      (Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.calendar_cell, null);
//        }
//        if (record != null) {
//
//            TextView tt = (TextView) view.findViewById(R.id.date);
//            TextView tt1 = (TextView) view.findViewById(R.id.intensity);
//            TextView tt3 = (TextView) view.findViewById(R.id.hours);
//
//            if (tt != null) {
//            	Calendar cal = record.getStart();
//            	if(cal != null)
//                tt.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)+" " + cal.get(Calendar.DATE) + ", " + cal.get(Calendar.YEAR));
//            }
//            if (tt1 != null) {
//            	if(record.getIntensity() == -1){
//            		tt1.setText("Intensity: (not set)");
//            	}
//            	else{tt1.setText("Intensity: " + record.getIntensity());}
//                
//            }else{ tt.setText("date and intensity is null");}
//            if (tt3 != null) {
//            	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyy 'at' hh:mm aa", Locale.getDefault());
//            		if((record.getEnd() != null) && (record.getStart() != null))
//                tt3.setText(dateFormat.format(record.getStart().getTime()) + "\nfor " + CalendarUtil.getDuration(record.getStart(), record.getEnd()));
//            		else if (record.getStart() != null)
//            			tt3.setText(dateFormat.format(record.getStart().getTime()));
//            	
//            }
//        }
//        
//        
//    	
//        
//        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.delViewButton);
//        deleteButton.setTag(position);
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(final View v) {
//            	AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage("Are you sure you want to delete this record?")
//                .setPositiveButton("Yes",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                            	deleteRecord(v);
//                            }
//                        }).setNegativeButton("No",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//            	AlertDialog alert = builder.create();
//            	alert.show();
//            }
//        });
//        
//        ImageButton editButton = (ImageButton) view.findViewById(R.id.editViewButton);
//        editButton.setTag(position);
//        editButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(final View v) {
//            	Intent intent = new Intent(context, ModifyRecordActivity.class);
//        		intent.putExtra("Record_ID", record.getId());
//        		context.startActivity(intent);
//            }
//            });

       
        
        return view;
    }
    
    
    
    
    
    
    
    
}
