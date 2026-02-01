package com.rebeyka.acapi.entities.gameflow;

import java.io.Serializable;
import java.util.Date;

public class LogEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4678306308052836026L;

	private Date date;
	
	private Play parent;
	
	private String message;
	
	public LogEntry(Play parent, String message) {
		this.date = new Date(System.currentTimeMillis());
		this.parent = parent;
		this.message = message;
	}
	
	public Date getDate() {
		return date;
	}

	public Play getParent() {
		return parent;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "%s: %s - %s".formatted(date.toString(), parent.getName(), message);
	}
}
