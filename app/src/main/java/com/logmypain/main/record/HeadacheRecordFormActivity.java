package com.logmypain.main.record;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.logmypain.main.record.dialogs.AddTriggersDialogFragment;
import com.logmypain.main.record.dialogs.DateTimeDialogFragment;
import com.logmypain.tasks.ViewRecordsActivity;
import com.logmypain.utils.CalendarUtil;
import com.logmypain.utils.DatabaseHelper;
import com.logmypain.utils.HeadacheRecord;

import com.logmypain.main.R;

public class HeadacheRecordFormActivity extends FragmentActivity 
implements DateTimeDialogFragment.DateTimeDialogListener{
	private static final Time now = new Time();
	private static final Calendar nowCal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
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
	private ListView triggersListView;
	//private TriggersAdapter triggersAdapter;
	private Calendar startCal;
	private Calendar endCal;
	
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
        
        initializeFormFields();
	}
	
	private void initCreate(){
		this.record = new HeadacheRecord();
		this.startCal = nowCal;
	}
	
	private void setupFields(){
        startDateText = (TextView) findViewById(R.id.startDate);
        startTimeText = (TextView) findViewById(R.id.startTime);
        endDateText = (TextView) findViewById(R.id.endDate);
        endTimeText = (TextView) findViewById(R.id.endTime);
        intensitySeekBar = (SeekBar) findViewById(R.id.seekBar1);
        intensityInfo = (TextView) findViewById(R.id.intensityInfo);
        //triggersListView = (ListView) findViewById(R.id.list_triggers_form);
        //triggersAdapter = new TriggersAdapter(this,
        //        layout.triggers_form_list_item, new ArrayList<Trigger>(), new ArrayList<Trigger>());
        //triggersListView.setAdapter(triggersAdapter);
        setupSeekBar();
	}
	
	private void initializeFormFields(){
		setupFields();
		Intent intent = getIntent();
        long id = intent.getLongExtra("Record_ID", -1);
        if(id > -1){
	        this.record = this.db.getRecord(id);
	        startCal = record.getStart();
	        
	        endCal = record.getEnd();
	        
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
	        
	       /* List<Trigger> triggers = record.getTriggers();
	        if(triggers.size() > 0){
	        	triggersAdapter.setTriggers(triggers);
	        	triggersAdapter.notifyDataSetChanged();
	        }*/
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
    startActivity(new Intent(this, ViewRecordsActivity.class));

	}
	
	private void setupSeekBar(){
		
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
                case 3:
                	level = "Mild";
                	break;
                case 4:
                case 5:
                case 6:
                	level = "Moderate";
                	break;
                case 7:
                case 8:
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
	
	public void editTriggers(View view) {
		System.out.println("edit triggers clicked");
		AddTriggersDialogFragment newFragment = new AddTriggersDialogFragment();
		newFragment.show(getSupportFragmentManager(), "triggers");
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
	
	//@Override
	/*public void onReturnVal(List<Trigger> addTriggers, List<Trigger> removeTriggers) {
		// TODO Auto-generated method stub
		if(removeTriggers != null){
		for(Trigger remove : removeTriggers){
			triggersAdapter.remove(remove);
		}
		}
		if(addTriggers != null){
		for(Trigger add : addTriggers){
			triggersAdapter.add(add);
		}
		}
		triggersAdapter.notifyDataSetChanged();
	}*/
	
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
