package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;

public class ActionableCheck extends BacktrackingActionableCheck<Actionable, ActionableCheck> {

	protected ActionableCheck(ActionableCheck root, List<TestResult<Actionable>> testResults, Function<Actionable, Actionable> function) {
		super(root, testResults, function);
	}

	protected ActionableCheck(List<TestResult<Actionable>> testResults, Function<Actionable, Actionable> function) {
		super(testResults, function);
	}

	@Override
	protected ActionableCheck self(List<TestResult<Actionable>> testResults) {
		return new ActionableCheck(this,testResults,function);
	}
}
