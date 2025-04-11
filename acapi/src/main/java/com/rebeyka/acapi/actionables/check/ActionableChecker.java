package com.rebeyka.acapi.actionables.check;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;

public class ActionableChecker extends TypedActionableCheck<Actionable> {

	private ActionableChecker(Map<String, Predicate<Actionable>> tests) {
		super(tests, p -> p);
	}
	
	public static ActionableChecker whenActionable() {
		return new ActionableChecker(new HashMap<>());
	}
}
