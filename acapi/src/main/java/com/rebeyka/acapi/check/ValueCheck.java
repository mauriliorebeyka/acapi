package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

public abstract class ValueCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>, T>
		extends AbstractCheck<ValueCheck<BASE,ROOT,T>, ROOT, BASE, T> {

	protected Function<T, ?> valueAcessor;

	public ValueCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, T> function) {
		super(root, testResults, function);
	}

	public BacktrackingIntegerCheck<BASE, ROOT> asInt() {
		return new BacktrackingIntegerCheck<>(root, testResults, p -> (int) valueAcessor.apply(function.apply(p)));
	}

	public BacktrackingStringCheck<BASE, ROOT> asString() {
		return new BacktrackingStringCheck<BASE, ROOT>(root, testResults, p -> (String) valueAcessor.apply(function.apply(p)));
	}
}
