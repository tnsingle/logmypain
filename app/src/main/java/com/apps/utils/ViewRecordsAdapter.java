package com.apps.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.apps.headache.R;
import com.apps.headache.record.ModifyRecordActivity;
import com.apps.utils.HeadacheRecord;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewRecordsAdapter extends ArrayAdapter <HeadacheRecord>{

	private Context context; 
    private int layoutResourceId;    
    private List<HeadacheRecord> recordList;
    private long toDelete;
    
    public ViewRecordsAdapter(Context context, int layoutResourceId, List<HeadacheRecord> records) {
        super(context, layoutResourceId, records);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.recordList = records;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final HeadacheRecord record = recordList.get(position);
        System.out.println("list items: " + recordList.size());
        if (view == null){ // no view to re-use, create new
        	LayoutInflater inflater = (LayoutInflater)context.getSystemService
        		      (Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_view_records, null);
        }
        if (record != null) {

            TextView tt = (TextView) view.findViewById(R.id.date);
            TextView tt1 = (TextView) view.findViewById(R.id.intensity);
            TextView tt3 = (TextView) view.findViewById(R.id.hours);

            if (tt != null) {
            	Calendar cal = record.getStart();
            	if(cal != null)
                tt.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)+" " + cal.get(Calendar.DATE) + ", " + cal.get(Calendar.YEAR));
            }
            if (tt1 != null) {
            	if(record.getIntensity() == -1){
            		tt1.setText("Intensity: (not set)");
            	}
            	else{tt1.setText("Intensity: " + record.getIntensity());}
                
            }else{ tt.setText("date and intensity is null");}
            if (tt3 != null) {
            	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyy 'at' hh:mm aa", Locale.getDefault());
            		if((record.getEnd() != null) && (record.getStart() != null))
                tt3.setText(dateFormat.format(record.getStart().getTime()) + "\nfor " + CalendarUtil.getDuration(record.getStart(), record.getEnd()));
            		else if (record.getStart() != null)
            			tt3.setText(dateFormat.format(record.getStart().getTime()));
            	
            }
        }
        
        
    	
        
        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.delViewButton);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
            	AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this record?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            	deleteRecord(v);
                            }
                        }).setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
            	AlertDialog alert = builder.create();
            	alert.show();
            }
        });
        
        ImageButton editButton = (ImageButton) view.findViewById(R.id.editViewButton);
        editButton.setTag(position);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
            	Intent intent = new Intent(context, ModifyRecordActivity.class);
        		intent.putExtra("Record_ID", record.getId());
        		context.startActivity(intent);
            }
            });

       
        
        return view;
    }
    
    public void deleteRecord(View v){
    	 
         	   DatabaseHelper db = new DatabaseHelper(context);
                Integer index = (Integer) v.getTag();
                toDelete = recordList.get(index.intValue()).getId();
                db.deleteRecord(toDelete);
                recordList.remove(index.intValue());  
                notifyDataSetChanged();   
            
    
    }
    
    
    
    public String getNumHours(Calendar start, Calendar end){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'days' hh 'hours' mm 'minutes'", Locale.getDefault()); 
    	long totalMills = end.getTime().getTime() - start.getTime().getTime();
    	Calendar newCal = new GregorianCalendar();
    	newCal.setTimeInMillis(totalMills);
    	return dateFormat.format(newCal.getTime());
    }
    
    
}
