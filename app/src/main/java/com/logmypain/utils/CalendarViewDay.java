package com.logmypain.utils;

public class CalendarViewDay {
	private int day;
	private HeadacheRecord record;
	
	public CalendarViewDay(){
		
	}
	
	public CalendarViewDay(int day){
		this.setDay(day);
	}
	
	public CalendarViewDay(int day, HeadacheRecord record){
		this.setDay(day);
		this.setRecord(record);
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public HeadacheRecord getRecord() {
		return record;
	}

	public void setRecord(HeadacheRecord record) {
		this.record = record;
	}
}
