package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;
public class BacktrackingActionableCheck<BASE,ROOT extends AbstractCheck<?, ?, BASE, ?>> extends AbstractCheck<BacktrackingActionableCheck<BASE,ROOT>, ROOT, BASE, Actionable> {

	protected BacktrackingActionableCheck(List<TestResult<BASE>> testResults, Function<BASE, Actionable> function) {
		super(testResults, function, a -> function.apply(a).getParent().getGame());
	}

	protected BacktrackingActionableCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Actionable> function) {
		super(root, testResults, function, a -> function.apply(a).getParent().getGame());
	}
	
	@Override
	protected BacktrackingActionableCheck<BASE,ROOT> self(List<TestResult<BASE>> testResults) {
			return new BacktrackingActionableCheck<>(root, testResults, function);
	}
	
	public BacktrackingStringCheck<BASE, ROOT> hasId() {
		return new BacktrackingStringCheck<BASE, ROOT>(root, testResults, a -> function.apply(a).getId(), gameAcessor);
	}
	
	public BacktrackingPlayableCheck<BASE,ROOT> origin() {
		return new BacktrackingPlayableCheck<>(root, testResults, t -> function.apply(t).getParent().getOrigin());
	}
    
}
