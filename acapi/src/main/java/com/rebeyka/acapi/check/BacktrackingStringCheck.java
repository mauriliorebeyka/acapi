package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

public class BacktrackingStringCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>>
		extends AbstractCheck<BacktrackingStringCheck<BASE, ROOT>, ROOT, BASE, String> {

	protected BacktrackingStringCheck(ROOT root, List<TestResult<BASE>> testResults,  Function<BASE, String> function) {
		super(root, testResults, function);
	}

	protected BacktrackingStringCheck(List<TestResult<BASE>> testResults, Function<BASE, String> function) {
		super(testResults, function);
	}

	@Override
	protected BacktrackingStringCheck<BASE, ROOT> self(List<TestResult<BASE>> testResults) {
		return new BacktrackingStringCheck<>(root, testResults, function);
	}

	public ROOT contains(String value) {
		return addTest(s -> s.contains(value), value, "contains");
	}

}
