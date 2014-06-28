package com.apps.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class HeadacheRecord {

	private Calendar start;
	private Calendar end;
	private int intensity;
	private List <String> triggers;
	private List <String> meds;
	private long id;
	
	public HeadacheRecord () {
		this.intensity = -1;
		this.id = -1;
	}

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public void setStart(Date date) {
		this.start = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
		this.start.setTime(date);
	}

	public void setStartToNow() {
		this.start = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}

	public void setEnd(Date date) {
		this.end = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
		this.end.setTime(date);
	}

	public void setEndToNow() {
		this.end = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	public List <String> getTriggers() {
		return triggers;
	}

	public void setTriggers(List <String> triggers) {
		this.triggers = triggers;
	}

	public List <String> getMeds() {
		return meds;
	}

	public void setMeds(List <String> meds) {
		this.meds = meds;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString(){
		return this.start.toString() + " test " + this.id;
	}
}
