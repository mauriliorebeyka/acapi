package com.rebeyka.acapi.actionables.check;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;

public class ActionableChecker extends ActionableCheck<Actionable>{

	protected ActionableChecker() {
		super(new ArrayList<TestResult<Actionable>>(), Function.identity());
	}

	protected ActionableChecker(List<TestResult<Actionable>> tests, Function<Actionable, Actionable> function) {
		super(tests, function);
	}
}
