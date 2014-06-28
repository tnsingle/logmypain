package com.apps.headache;

import com.apps.headache.R;
import com.apps.utils.CalendarUtil;
import com.apps.utils.DatabaseHelper;
import com.apps.utils.HeadacheRecord;

import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.content.Intent; 

public class MainActivity extends Activity {
	public View dialog;
	public long Record_ID;
	private DatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db = new DatabaseHelper(this);
		FrameLayout rootLayout = (FrameLayout)findViewById(android.R.id.content);
		View.inflate(this, R.layout.dialog_record_now, rootLayout);
		dialog = findViewById(R.id.recordNowContent);
		dialog.setVisibility(View.GONE);
		Record_ID = -1;
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/** Called when the user clicks the Send button */
	public void recordHeadacheNow(View view) {
		//Intent intent = new Intent(this, RecordNowDialog.class);
		
	    //startActivity(intent);
		//View dialog = LayoutInflater.from(this).inflate(R.layout.dialog_record_now, null);
		HeadacheRecord record = new HeadacheRecord();
		
		
		String str = "Migraine recorded!<br/><b>";
		record.setStartToNow();
		str += CalendarUtil.getDateDisplay(record.getStart());
		str += "<br/>" + CalendarUtil.getTimeDisplay(record.getStart())+"</b>";
		Record_ID = db.addRecord(record);
		
		
		TextView message = (TextView)findViewById(R.id.recordDialogMessage);
		message.setText(Html.fromHtml(str));
		
		showDialogAnimated(dialog);
		
		Handler h = new Handler();
	    h.postDelayed(new Runnable() {

	        @Override
	        public void run() {
	            // EITHER HIDE IT IMMEDIATELY
	            //dialog.setVisibility(View.GONE);

	            // OR HIDE IT USING ANIMATION
	        	if(dialog.getVisibility() == View.VISIBLE)
	            hideDialogAnimated(dialog);

	            // DONT use both lines at the same time :)
	        }
	    }, 8000); // 8 seconds
		
		//RecordNowDialogFragment dialog = new RecordNowDialogFragment();
		//dialog.show(this.getSupportFragmentManager(), "recordNow");
	}
	
	public void createRecord(View view){
		Intent intent = new Intent(this, ModifyRecordActivity.class);
		startActivity(intent);
	}
	
	public void onClickEdit(View view){
		Intent intent = new Intent(this, ModifyRecordActivity.class);
		intent.putExtra("Record_ID", Record_ID);
	    startActivity(intent);
	}
	
	public void onClickUndo(View view){
		db.deleteRecord(Record_ID);
		hideDialogAnimated(dialog);
	}
	
	/** Called when the user clicks the Send button */
	public void viewRecords(View view) {
		Intent intent = new Intent(this, ViewRecordsActivity.class);
		
	    startActivity(intent);
	}
	
	public void hideDialogAnimated(final View v) {

	    Animation alpha = new AlphaAnimation(1.0f, 0.0f);
	    alpha.setDuration(1000); // whatever duration you want

	    // add AnimationListener
	    alpha.setAnimationListener(new AnimationListener(){

	        @Override
	        public void onAnimationEnd(Animation arg0) {
	            v.setVisibility(View.GONE);
	        }

	        @Override
	        public void onAnimationRepeat(Animation arg0) { }

	        @Override
	        public void onAnimationStart(Animation arg0) { }

	    });

	    v.startAnimation(alpha);
	}
	
	public void showDialogAnimated(final View v) {

	    Animation alpha = new AlphaAnimation(0.0f, 1.0f);
	    alpha.setDuration(1000); // whatever duration you want

	    // add AnimationListener
	    alpha.setAnimationListener(new AnimationListener(){

	        @Override
	        public void onAnimationEnd(Animation arg0) {
	           
	        }

	        @Override
	        public void onAnimationRepeat(Animation arg0) { }

	        @Override
	        public void onAnimationStart(Animation arg0) {
	        	v.setVisibility(View.VISIBLE);
	        }

	    });

	    v.startAnimation(alpha);
	}

}
