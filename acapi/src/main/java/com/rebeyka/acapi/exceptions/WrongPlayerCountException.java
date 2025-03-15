package com.rebeyka.acapi.exceptions;

public class WrongPlayerCountException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433251910318371524L;

	public WrongPlayerCountException(String message) {
		super(message);
	}
}
