package com.apps.headache.record.Dialogs;

import java.util.List;

import com.apps.headache.record.ModifyRecordActivity;
import com.apps.headache.record.Dialogs.DateTimeDialogFragment.DateTimeDialogListener;
import com.apps.logmypain.R;
import com.apps.logmypain.R.layout;
import com.apps.utils.DatabaseHelper;
import com.apps.utils.TriggersAdapter;
import com.apps.utils.ViewRecordsAdapter;
import com.apps.utils.Models.Trigger;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddTriggersDialogFragment extends DialogFragment implements OnClickListener{
	private List<Trigger> triggers;
	private List<Trigger> removed;
	private TriggersAdapter rAdapter;
	private View view;
	private ListView list;
	
	public interface AddTriggersDialogListener {
        public void onReturnVal(List<Trigger> triggers, List<Trigger> removed);
    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    // Get the layout inflater
	    view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_triggers, null);
		list = (ListView) view.findViewById(R.id.list_triggers);
		
		final Button addTriggerButton = (Button) view.findViewById(R.id.trigger_add_button);
	    
		DatabaseHelper db = new DatabaseHelper(this.getActivity());
		rAdapter = new TriggersAdapter(this.getActivity(),
                layout.triggers_list_item, db.getAllTriggers());
		list.setAdapter(rAdapter); 
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		list.setItemChecked(0, true);
		
		addTriggerButton.setOnClickListener(this);
		
	    // Add action buttons
	    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   AddTriggersDialogListener activity = (AddTriggersDialogListener) getActivity();
	            	    activity.onReturnVal(rAdapter.getTriggersToAdd(), rAdapter.getTriggersToRemove());
	               }
	           });
	    
	    builder.setView(view);
	    return builder.create();
	}
	
	public void addTrigger(){
		final EditText triggerTextField = (EditText) this.view.findViewById(R.id.trigger_text_field);
		Trigger trigger = new Trigger();
		trigger.setName(triggerTextField.getText().toString());
		DatabaseHelper db = new DatabaseHelper(this.getActivity());
		long id = db.addTrigger(trigger);
		trigger.setId(id);
		rAdapter.insert(trigger, 0);
		triggerTextField.setText("");
		rAdapter.notifyDataSetChanged();
		list.setItemChecked(0, true);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
        case R.id.trigger_add_button:
        	addTrigger();
            break;
        }
		
	}
}
