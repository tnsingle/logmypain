package com.logmypain.main.record;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.logmypain.main.DateTimeDialogFragment;
import com.logmypain.main.R;
import com.logmypain.utils.CalendarUtil;
import com.logmypain.utils.DatabaseHelper;
import com.logmypain.utils.HeadacheRecord;

public abstract class HeadacheRecordFormAbstractActivity extends FragmentActivity implements DateTimeDialogFragment.DateTimeDialogListener{
	public static final Time now = new Time();
	public static final Calendar nowCal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
	public HeadacheRecord record; 
	public DatabaseHelper db;
	public SeekBar intensitySeekBar;
	public TextView intensityInfo;
	public TextView activeDateDisplay;
	public TextView activeTimeDisplay;
	public TextView startDateText;
	public TextView startTimeText;
	public TextView endDateText;
	public TextView endTimeText;
	public Calendar startCal;
	public Calendar endCal;
	
	public enum ActiveDateTimeField{
		START,
		END
	}
	
	private ActiveDateTimeField activeDateTimeField;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_record);
		
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.db = new DatabaseHelper(this);
        setupSeekBar();
        initializeFormFields();
	}
	
	protected abstract void initializeFormFields();
	public abstract void saveRecord(View view);
	
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

	public void editStartDateTime(View view) {
		this.activeDateTimeField = ActiveDateTimeField.START;
		DialogFragment newFragment = new DateTimeDialogFragment();
		
	    newFragment.setArguments(CalendarUtil.bundleCalendar(this.startCal));
	    newFragment.show(getSupportFragmentManager(), "datetime");
	}
	
	public void editEndDateTime(View view) {
		this.activeDateTimeField = ActiveDateTimeField.END;
		DialogFragment newFragment = new DateTimeDialogFragment();
		newFragment.setArguments(CalendarUtil.bundleCalendar(this.endCal, this.startCal.getTimeInMillis()));
	    newFragment.show(getSupportFragmentManager(), "datetime");
	}

	@Override
	public void onReturnVal(DatePicker date, TimePicker time) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
		int hour = time.getCurrentHour();
		cal.set(date.getYear(), date.getMonth(), date.getDayOfMonth(), hour, time.getCurrentMinute());
		
		switch(activeDateTimeField){
			case START:
				updateStartDateTimeFields(cal);
				break;
			case END:
				updateEndDateTimeFields(cal);
				break;
			default:
				break;
		}
	}
	
	private void updateStartDateTimeFields(Calendar cal){
		this.startCal = cal;
		this.startDateText.setText(CalendarUtil.getDateDisplay(cal));
		this.startTimeText.setText(CalendarUtil.getTimeDisplay(cal));
	}
	
	private void updateEndDateTimeFields(Calendar cal){
		this.endCal = cal;
		this.endDateText.setText(CalendarUtil.getDateDisplay(cal));
		this.endTimeText.setText(CalendarUtil.getTimeDisplay(cal));
	}
	
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        case android.R.id.home: 
            onBackPressed();
            break;

        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
