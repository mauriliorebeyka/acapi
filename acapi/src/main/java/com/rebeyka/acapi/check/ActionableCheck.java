package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;
public class ActionableCheck<BASE,ROOT extends AbstractCheck<?, ?, BASE, ?>> extends AbstractCheck<ActionableCheck<BASE,ROOT>, ROOT, BASE, Actionable> {

	protected ActionableCheck(List<TestResult<BASE>> testResults, Function<BASE, Actionable> function) {
		super(testResults, function, a -> function.apply(a).getParent().getGame());
	}

	protected ActionableCheck(ROOT root, Function<BASE, Actionable> function) {
		super(root, function, a -> function.apply(a).getParent().getGame());
	}
	
	@Override
	protected ActionableCheck<BASE,ROOT> self() {
			return new ActionableCheck<>(root, this.function);
	}
	
	public StringCheck<BASE, ROOT> hasId() {
		return new StringCheck<BASE, ROOT>(root, a -> function.apply(a).getActionableId(), gameAcessor);
	}
	
	public PlayableCheck<BASE,ROOT> origin() {
		return new PlayableCheck<>(root, t -> function.apply(t).getParent().getOrigin());
	}
    
}
