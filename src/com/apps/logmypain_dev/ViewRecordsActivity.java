package com.apps.logmypain_dev;

import com.apps.logmypain.R;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ViewRecordsActivity extends ActionBarActivity {
	//private ViewRecordsAdapter rAdapter;
	ActionBar.Tab listTab, calendarTab;
	Fragment viewListFragment = new ViewListFragment();
	Fragment viewCalendarFragment = new ViewCalendarFragment();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records_tabs);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        listTab = actionBar.newTab().setText("List");
        calendarTab = actionBar.newTab().setText("Calendar");
        
        listTab.setIcon(R.drawable.ic_action_view_as_list);
        calendarTab.setIcon(R.drawable.ic_action_view_as_calendar);
        
        listTab.setTabListener(new ViewRecordsTabListener(viewListFragment));
        calendarTab.setTabListener(new ViewRecordsTabListener(viewCalendarFragment));
        
        actionBar.addTab(listTab);
        actionBar.addTab(calendarTab);
		//setContentView(R.layout.listview_view_records);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		//DatabaseHelper db = new DatabaseHelper(this);
		//rAdapter = new ViewRecordsAdapter(ViewRecordsActivity.this,
        //        layout.activity_view_records, db.getAllRecords());
		//setListAdapter(rAdapter);
        
        Intent intent = getIntent();
        int activeTab = intent.getIntExtra("ActiveTab", 0);
        actionBar.setSelectedNavigationItem(activeTab);
        
        
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_records, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
//	        case R.id.action_view_as_calendar:
//	        	Intent intent = new Intent(this, ViewCalendarActivity.class);	    		
//	    	    startActivity(intent);
//	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
//	public void onClickDeleteRecord(View v){
//		rAdapter.deleteRecord(v);
//    
//    }
	
	
	@Override
	public void onBackPressed()
	{
	Intent mIntent= new Intent(this, MainActivity.class);
	    startActivity(mIntent);
	    mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    finish();
	}

	public void viewPrevMonth(View view){
		((ViewCalendarFragment)viewCalendarFragment).viewPrevMonth(view);
	}
	
	public void viewNextMonth(View view){
		((ViewCalendarFragment)viewCalendarFragment).viewNextMonth(view);
	}
	
	

}


