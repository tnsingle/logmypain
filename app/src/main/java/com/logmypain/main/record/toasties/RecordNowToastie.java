package com.logmypain.main.record.toasties;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.logmypain.R;

public class RecordNowToastie extends Dialog {
	public RecordNowToastie(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Pass touch events to the background activity
        Window window = getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.addFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		setContentView(R.layout.dialog_record_now);
		//TextView message = (TextView) findViewById(R.id.dialog_record_now);
	    
	    //	message.setText("Migraine recorded for ");
	    
	   
	}
	
	

}
