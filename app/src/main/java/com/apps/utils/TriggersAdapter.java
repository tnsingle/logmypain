package com.apps.utils;

import java.util.ArrayList;
import java.util.List;

import com.apps.headache.R;
import com.apps.utils.Models.Headache;
import com.apps.utils.Models.Trigger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class TriggersAdapter extends ArrayAdapter<Trigger>{

	private Context context; 
    private int resource;    
    private List<Trigger> triggers;    
    private List<Trigger> headacheTriggers;    
    private List<Trigger> triggersToAdd;    
    private List<Trigger> triggersToRemove;
    
	public TriggersAdapter(Context context, int resource, List<Trigger> triggers, List<Trigger> headacheTriggers) {
		super(context, resource, triggers);
		this.context = context;
		this.resource = resource;
		this.setTriggers(triggers);
		
		//this.triggersToAdd = new ArrayList<Trigger>();
		this.triggersToRemove = new ArrayList<Trigger>();

	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final Trigger trigger = getTriggers().get(position);

        if (view == null){ // no view to re-use, create new
        	LayoutInflater inflater = (LayoutInflater)context.getSystemService
        		      (Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.resource, null);
        }
        if(trigger == null) return view;
        if(parent.getId() == R.id.list_triggers){
	        final CheckedTextView triggerItem = (CheckedTextView) view.findViewById(R.id.trigger_checkbox);
	        	if (headacheTriggers.contains(trigger))
	        		triggerItem.setChecked(true);
	        	else
	        		triggerItem.setChecked(false);
	        	triggerItem.setText(trigger.getName());
	        	triggerItem.setOnClickListener(new OnClickListener(){
	
					@Override
					public void onClick(View v) {
						triggerItem.toggle();
						if(!triggerItem.isChecked()){
							getTriggersToRemove().add(trigger);
							getTriggersToAdd().remove(trigger);
						}else if(triggerItem.isChecked()){
							getTriggersToRemove().remove(trigger);
							getTriggersToAdd().add(trigger);
						}
					}
	        		
	        	});
	        
        }/*else if(parent.getId() == R.id.list_triggers_form){
        	final TextView tv = (TextView) view.findViewById(R.id.trigger_text_item);
        	tv.setText(trigger.getName());
        }*/
        
        
        return view;
	}
	

	public List<Trigger> getTriggersToRemove() {
		return triggersToRemove;
	}

	public void setTriggersToRemove(List<Trigger> triggersToRemove) {
		this.triggersToRemove = triggersToRemove;
	}

	public List<Trigger> getTriggersToAdd() {
		return triggersToAdd;
	}

	public void setTriggersToAdd(List<Trigger> triggersToAdd) {
		this.triggersToAdd = triggersToAdd;
	}

	public List<Trigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}

//	private List<Trigger> getTriggersToAdd() {
//		return triggersToAdd;
//	}
//
//	private void setTriggersToAdd(List<Trigger> triggersToAdd) {
//		this.triggersToAdd = triggersToAdd;
//	}


}
