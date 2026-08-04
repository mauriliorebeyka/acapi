package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;
public class ActionableCheck<BASE,ROOT extends AbstractCheck<?, ?, BASE, ?>> extends AbstractCheck<ActionableCheck<BASE,ROOT>, ROOT, BASE, Actionable> {

	protected ActionableCheck(List<TestResult<BASE>> testResults, Function<BASE, Actionable> function) {
		super(testResults, function, a -> function.apply(a).getParent().getGame());
	}

	protected ActionableCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Actionable> function) {
		super(root, testResults, function, a -> function.apply(a).getParent().getGame());
	}
	
	@Override
	protected ActionableCheck<BASE,ROOT> self(List<TestResult<BASE>> testResults) {
			return new ActionableCheck<>(root, testResults, function);
	}
	
	public StringCheck<BASE, ROOT> hasId() {
		return new StringCheck<BASE, ROOT>(root, testResults, a -> function.apply(a).getActionableId(), gameAcessor);
	}
	
	public PlayableCheck<BASE,ROOT> origin() {
		return new PlayableCheck<>(root, testResults, t -> function.apply(t).getParent().getOrigin());
	}
    
}
