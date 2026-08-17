package com.rebeyka.acapi.check;

import java.util.List;

public abstract class Checkable<BASE> {

	protected List<TestResult<BASE>> testResults;

	protected boolean negate;
	
	public abstract boolean check(BASE value);
}
