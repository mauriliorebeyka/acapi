package com.rebeyka.acapi.actionables;

import java.util.function.Supplier;

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

	@Override
	public Supplier<Actionable> supply() {
		return () -> new EmptyActionable();
	}
}
