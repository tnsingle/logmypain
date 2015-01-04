package com.logmypain.tasks.framents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.logmypain.tasks.listeners.YearSelectedListener;
import com.logmypain.utils.DatabaseHelper;
import com.logmypain.tasks.adapters.ViewRecordsAdapter;
import com.logmypain.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewListFragment extends Fragment{
	private ViewRecordsAdapter rAdapter;
	View view; 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
            Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = inflater.inflate(R.layout.listview_view_records, container, false);
		final ListView list = (ListView) view.findViewById(R.id.list_view_records);
		final TextView emptyList = (TextView) view.findViewById(R.id.list_view_empty);
		//getActionBar().setDisplayHomeAsUpEnabled(true);

            DatabaseHelper db = new DatabaseHelper(this.getActivity());

		//rAdapter = new ViewRecordsAdapter(this.getActivity(),
        //       layout.activity_view_records, db.getAllHeadaches());
		//list.setAdapter(rAdapter);
		list.setEmptyView(emptyList);

        List<String> years = db.getYears();
		setupSpinners(list, years);

		return view;
	}

	public void setupSpinners(ListView list, List<String> years){
		if(years == null || years.size() == 0){
			years = new ArrayList<String>();
			Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
			int year = cal.get(Calendar.YEAR);
			years.add(Integer.toString(year));
		}
		Spinner yearSpinner = (Spinner) view.findViewById(R.id.view_records_year_spinner);
		Spinner monthSpinner = (Spinner) view.findViewById(R.id.view_records_month_spinner);
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(),
			android.R.layout.simple_spinner_item, years);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		yearSpinner.setAdapter(dataAdapter);
		   
		yearSpinner.setOnItemSelectedListener(new YearSelectedListener(list, monthSpinner, YearSelectedListener.Change.YEAR));
		monthSpinner.setOnItemSelectedListener(new YearSelectedListener(list, yearSpinner, YearSelectedListener.Change.MONTH));

	}
	
	public void onClickDeleteRecord(View v){
		rAdapter.deleteRecord(v);
    
    }

	
	
//	

}
