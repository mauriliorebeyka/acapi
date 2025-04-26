package com.rebeyka.acapi.actionables.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;
public class ActionableCheck<BASE> extends AbstractCheck<ActionableCheck<BASE>, BASE, Actionable> {

	protected ActionableCheck(List<TestResult<BASE>> testResults, Function<BASE, Actionable> function) {
		super(testResults, function);
	}
	
	public StringCheck<BASE, Actionable, ActionableCheck<BASE>> id() {
		return new StringCheck<>(this, a -> function.apply(a).getActionableId(), "Actionable ID");
	}
	
	public PlayableCheck<BASE> origin() {
		return new PlayableCheck<>(testResults, t -> function.apply(t).getParent().getOrigin());
	}

	public TimelineCheck<BASE, Actionable, ActionableCheck<BASE>> happened() {
		return happened("this actionable");
	}
	
	public TimelineCheck<BASE, Actionable, ActionableCheck<BASE>> happened(String actionableId) {
		return new TimelineCheck<>(this, g -> function.apply(g).getParent().getGame(), actionableId);
	}
}
