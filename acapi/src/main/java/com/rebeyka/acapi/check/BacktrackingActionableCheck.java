package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;
public class BacktrackingActionableCheck<BASE,ROOT extends AbstractCheck<?, ?, BASE, ?>> extends GameEntityCheck<BASE, ROOT, Actionable> {

	protected BacktrackingActionableCheck(List<TestResult<BASE>> testResults, Function<BASE, Actionable> function) {
		super(testResults, function);
	}

	protected BacktrackingActionableCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Actionable> function) {
		super(root, testResults, function);
	}
	
	@Override
	protected BacktrackingActionableCheck<BASE,ROOT> self(List<TestResult<BASE>> testResults) {
			return new BacktrackingActionableCheck<>(root, testResults, function);
	}
	
	public BacktrackingPlayableCheck<BASE,ROOT> origin() {
		return new BacktrackingPlayableCheck<>(root, testResults, t -> function.apply(t).getParent().getOrigin());
	}
    
}
