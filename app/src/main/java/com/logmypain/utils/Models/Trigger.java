package com.logmypain.utils.Models;

public class Trigger {
	private long id;
	private String name;
	
	public Trigger() {
		this.setId(-1);
		this.setName("");
	}
	
	public Trigger(String name) {
		this.setId(-1);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
