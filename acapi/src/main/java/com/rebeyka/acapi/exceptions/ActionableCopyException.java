package com.rebeyka.acapi.exceptions;

public class ActionableCopyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7471839897714511034L;

	public ActionableCopyException(Exception e) {
		super("Unable to copy Actionable",e);
	}
	
}
