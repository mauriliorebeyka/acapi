package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;

public class ActionableCheck extends BacktrackingActionableCheck<Actionable, ActionableCheck>
		implements RootChecker<Actionable, ActionableCheck> {

	protected ActionableCheck(ActionableCheck root, Function<Actionable, Actionable> function) {
		super(root, function);
	}

	protected ActionableCheck() {
		super(null, Function.identity());
	}

	@Override
	public ActionableCheck self() {
		return new ActionableCheck(this, function);
	}

}
