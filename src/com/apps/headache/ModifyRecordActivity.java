package com.apps.headache;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import com.apps.utils.CalendarUtil;
import com.apps.utils.DatabaseHelper;
import com.apps.utils.HeadacheRecord;
import com.apps.headache.R;

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

public class ModifyRecordActivity extends FragmentActivity implements DateTimeDialogFragment.DateTimeDialogListener{
	
	public static final Time now = new Time();
	public static final Calendar nowCal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
	private HeadacheRecord record; 
	private DatabaseHelper db;
	private SeekBar intensitySeekBar;
	private TextView intensityInfo;
	private TextView activeDateDisplay;
	private TextView activeTimeDisplay;
	private TextView startDateText;
    private TextView startTimeText;
	private TextView endDateText;
    private TextView endTimeText;
    private Calendar startCal;
    private Calendar endCal;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_record);
		
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        
        
        Intent intent = getIntent();
        long id = intent.getLongExtra("Record_ID", -1);
        this.db = new DatabaseHelper(this);
        setupSeekBar();
        
        if(id > -1){
        	init(id);
        }else{
        	init();
        }
        
	}
	private void init(){
		this.record = new HeadacheRecord();
		this.startCal = nowCal;
        startDateText = (TextView) findViewById(R.id.startDate);
        startTimeText = (TextView) findViewById(R.id.startTime);
        endDateText = (TextView) findViewById(R.id.endDate);
        endTimeText = (TextView) findViewById(R.id.endTime);
	}
	private void init(long id){
		
        this.record = this.db.getRecord(id);
        System.out.println("Record Id " + id);
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
	}
	
	private void setupSeekBar(){
		intensitySeekBar = (SeekBar) findViewById(R.id.seekBar1);
        intensityInfo = (TextView) findViewById(R.id.intensityInfo);
        intensitySeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {

            @Override
            public void onProgressChanged(SeekBar seekbar, int progress,
                    boolean fromUser) {
                // TODO Auto-generated method stub
            	String level = "";
                switch(progress){
                case 0:
                	level = "No Pain";
                	break;
                case 1:
                case 2:
                	level = "Mild";
                	break;
                case 3:
                case 4:
                	level = "Mild-Moderate";
                	break;
                case 5:
                case 6:
                	level = "Moderate";
                	break;
                case 7:
                case 8:
                	level = "Moderate-Severe";
                	break;
                case 9:
                case 10:
                	level = "Severe";
                	break;
            	default:
            		level = "";
                }
                intensityInfo.setText(String.valueOf(progress) + ": " + level);
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_headache_now, menu);
		return true;
	}
	
	public void onClick(View v) {
        
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
	
	public void editStartDateTime(View view) {
		this.activeDateDisplay = this.startDateText;
		this.activeTimeDisplay = this.startTimeText;
		DialogFragment newFragment = new DateTimeDialogFragment();
		
	    newFragment.setArguments(bundleCalendar(startCal));
	    newFragment.show(getSupportFragmentManager(), "datetime");
	}
	
	public void editEndDateTime(View view) {
		this.activeDateDisplay = this.endDateText;
		this.activeTimeDisplay = this.endTimeText;
		DialogFragment newFragment = new DateTimeDialogFragment();
		newFragment.setArguments(bundleCalendar(endCal));
	    newFragment.show(getSupportFragmentManager(), "datetime");
	}

	@Override
	public void onReturnVal(DatePicker date, TimePicker time) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
		int hour = time.getCurrentHour();
		cal.set(date.getYear(), date.getMonth(), date.getDayOfMonth(), hour, time.getCurrentMinute());
		
		//if (hour >= 12){
		//	cal.set(Calendar.AM_PM, Calendar.PM);
		//}
		
		if(activeDateDisplay == startDateText){
			startCal = cal;
		}else if(activeDateDisplay == endDateText){
			endCal = cal;
		}
		//System.out.println("Returned datetime: " + date.getYear() + " " + date.getMonth() + " " + date.getDayOfMonth() + " " + hour + "cal hour: " + cal.get(Calendar.HOUR_OF_DAY) + ":" + time.getCurrentMinute());
		//System.out.println("Printed datetime: " + CalendarUtil.getDateDisplay(cal) + " " +CalendarUtil.getTimeDisplay(cal));
		this.activeDateDisplay.setText(CalendarUtil.getDateDisplay(cal));
		this.activeTimeDisplay.setText(CalendarUtil.getTimeDisplay(cal));
		
	}
	
	private Bundle bundleCalendar(Calendar cal){
		Bundle args = new Bundle();
		if (cal == null)
			cal = nowCal;
	    args.putInt("year", startCal.get(Calendar.YEAR));
	    args.putInt("month", startCal.get(Calendar.MONTH));
	    args.putInt("day", startCal.get(Calendar.DAY_OF_MONTH));
	    args.putInt("hour", startCal.get(Calendar.HOUR_OF_DAY));
	    args.putInt("minute", startCal.get(Calendar.MINUTE));
	    return args;
	}
	

}
