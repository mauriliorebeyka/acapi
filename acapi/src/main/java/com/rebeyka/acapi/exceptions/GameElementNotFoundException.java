package com.rebeyka.acapi.exceptions;

public class GameElementNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2253927108730450322L;

	public GameElementNotFoundException(String message) {
		super(message);
	}
	
	public GameElementNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
