package com.apps.headache.record;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import com.apps.utils.CalendarUtil;
import com.apps.utils.DatabaseHelper;
import com.apps.utils.HeadacheRecord;
import com.apps.headache.DateTimeDialogFragment;
import com.apps.headache.R;
import com.apps.headache.ViewRecordsActivity;
import com.apps.headache.R.id;
import com.apps.headache.R.menu;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

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
