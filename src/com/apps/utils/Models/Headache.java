package com.apps.utils.Models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Headache {

	private Calendar start;
	private Calendar end;
	private int intensity;
	private long id;
	private List<Trigger> triggers;
	
	public Headache () {
		this.intensity = -1;
		this.id = -1;
		this.triggers = new ArrayList<Trigger>();
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

	public List<Trigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<Trigger> triggers) {
		this.triggers = triggers;
	}
}
