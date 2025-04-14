package com.rebeyka.acapi.actionables.check;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;
public class ActionableCheck<BASE> extends AbstractCheck<BASE, Actionable> {

	ActionableCheck(Map<String, Predicate<BASE>> tests, Function<BASE, Actionable> function) {
		super(tests, function);
	}
	
	public StringCheck<BASE, Actionable, ActionableCheck<BASE>> id() {
		return new StringCheck<>(tests, Actionable::getActionableId, "Actionable ID", this);
	}
	
	public PlayableCheck<BASE> origin() {
		return new PlayableCheck<BASE>(tests, t -> function.apply(t).getParent().getOrigin());
	}

}
