package com.rebeyka.acapi.actionables;

public class EmptyActionable extends Actionable{

	public EmptyActionable() {
		super("EMPTY");
	}

	@Override
	public void execute() {
		
	}

	@Override
	public void rollback() {
		
	}

	@Override
	public String getMessage() {
		return "empty";
	}

}
