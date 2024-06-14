package com.rebeyka.acapi.exceptions;

public class DieAlreadyRolledException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5827674461979501126L;

	public DieAlreadyRolledException() {
		super("Die already rolled and rerolls are not permitted");
	}
}
