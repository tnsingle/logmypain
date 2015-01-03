package com.logmypain.main.record;

import com.logmypain.utils.CalendarUtil;
import com.logmypain.utils.HeadacheRecord;
import com.logmypain.main.R;
import com.logmypain.main.ViewRecordsActivity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class ModifyRecordActivity extends HeadacheRecordFormAbstractActivity{
	
	private void initCreate(){
		this.record = new HeadacheRecord();
		this.startCal = nowCal;
        startDateText = (TextView) findViewById(R.id.startDate);
        startTimeText = (TextView) findViewById(R.id.startTime);
        endDateText = (TextView) findViewById(R.id.endDate);
        endTimeText = (TextView) findViewById(R.id.endTime);
	}
	
	protected void initializeFormFields(){
		Intent intent = getIntent();
        long id = intent.getLongExtra("Record_ID", -1);
        if(id > -1){
	        this.record = this.db.getRecord(id);
	        startCal = record.getStart();
	        startDateText = (TextView) findViewById(R.id.startDate);
	        startTimeText = (TextView) findViewById(R.id.startTime);
	        
	        endCal = record.getEnd();
	        endDateText = (TextView) findViewById(R.id.endDate);
	        endTimeText = (TextView) findViewById(R.id.endTime);
	        
	        if(startCal != null){
	        startDateText.setText(CalendarUtil.getDateDisplay(startCal));
	        startTimeText.setText(CalendarUtil.getTimeDisplay(startCal));
	        }
	        
	        if(endCal != null){
	            endDateText.setText(CalendarUtil.getDateDisplay(endCal));
	            endTimeText.setText(CalendarUtil.getTimeDisplay(endCal));
	            }
	        
	        int intensity = record.getIntensity();
	        
	        
	        if(intensity > -1){
	        	intensitySeekBar.setProgress(intensity);
	        }
        }else{
        	initCreate();
        }
	}
	
	
	
public void saveRecord(View view) {
        
        record.setIntensity(intensitySeekBar.getProgress());
        record.setStart(startCal);
        record.setEnd(endCal);
        if(record.getId() > -1)
        	db.updateRecord(record);
        else
        	db.addRecord(record);
		Intent intent = new Intent(this, ViewRecordsActivity.class);
		
	    startActivity(intent);
	}
	
	

}
