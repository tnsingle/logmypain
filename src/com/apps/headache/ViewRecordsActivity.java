package com.apps.headache;

import com.apps.utils.DatabaseHelper;
import com.apps.utils.ViewRecordsAdapter;
import com.apps.headache.R;
import com.apps.headache.R.layout;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ViewRecordsActivity extends ListActivity {
	private ViewRecordsAdapter rAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_view_records);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		DatabaseHelper db = new DatabaseHelper(this);
		rAdapter = new ViewRecordsAdapter(ViewRecordsActivity.this,
                layout.activity_view_records, db.getAllRecords());
		setListAdapter(rAdapter);
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_records, menu);
		return true;
	}
	
	public void onClickDeleteRecord(View v){
		rAdapter.deleteRecord(v);
    
    }
	
	@Override
	public void onBackPressed()
	{
	Intent mIntent= new Intent(this, MainActivity.class);
	    startActivity(mIntent);
	    mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    finish();
	}

}
