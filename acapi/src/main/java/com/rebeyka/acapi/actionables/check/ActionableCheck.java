package com.rebeyka.acapi.actionables.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Game;
public class ActionableCheck<BASE> extends AbstractCheck<BASE, Actionable> {

	protected ActionableCheck(List<TestResult<BASE>> testResults, Function<BASE, Actionable> function) {
		super(testResults, function);
	}
	
	public StringCheck<BASE, Actionable, ActionableCheck<BASE>> id() {
		return new StringCheck<>(this, Actionable::getActionableId, "Actionable ID");
	}
	
	public PlayableCheck<BASE> origin() {
		return new PlayableCheck<BASE>(testResults, t -> function.apply(t).getParent().getOrigin());
	}

	public TimelineCheck<BASE, Actionable, Actionable, ActionableCheck<BASE>> happened() {
		return happened("");
	}
	
	public TimelineCheck<BASE, Actionable, Actionable, ActionableCheck<BASE>> happened(String actionableId) {
		return new TimelineCheck<>(this, f -> f, actionableId, g -> g.getParent().getGame());
	}
}
