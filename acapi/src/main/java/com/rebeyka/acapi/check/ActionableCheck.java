package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;
public class ActionableCheck<BASE> extends AbstractCheck<ActionableCheck<BASE>, BASE, Actionable> {

	protected ActionableCheck(List<TestResult<BASE>> testResults, Function<BASE, Actionable> function) {
		super(testResults, function, a -> function.apply(a).getParent().getGame());
	}

	@Override
	protected ActionableCheck<BASE> self() {
			return new ActionableCheck<>(testResults, this.function);
	}
	
	public StringCheck<BASE, ActionableCheck<BASE>> hasId() {
		return new StringCheck<>(this, a -> function.apply(a).getActionableId(), "Actionable ID", a -> function.apply(a).getParent().getGame());
	}
	
	public PlayableCheck<BASE> origin() {
		return new PlayableCheck<>(testResults, t -> function.apply(t).getParent().getOrigin());
	}
    
}
