package com.rebeyka.acapi.exceptions;

public class DieNotRolledException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5120870031967246691L;

	public DieNotRolledException() {
		super("Die not rolled");
	}
}
