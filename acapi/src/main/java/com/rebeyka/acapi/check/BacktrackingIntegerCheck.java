package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

public class BacktrackingIntegerCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>>
		extends AbstractCheck<BacktrackingIntegerCheck<BASE, ROOT>, ROOT, BASE, Integer> {

	protected BacktrackingIntegerCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Integer> function) {
		super(root, testResults, function);
	}

	protected BacktrackingIntegerCheck(List<TestResult<BASE>> testResults, Function<BASE, Integer> function) {
		super(testResults, function);
	}

	@Override
	protected BacktrackingIntegerCheck<BASE, ROOT> self(List<TestResult<BASE>> testResults) {
		return new BacktrackingIntegerCheck<>(root, testResults, function);
	}

	public ROOT biggerThan(int value) {
		return addTest(i -> i > value, Integer.toString(value), "is bigger than");
	}

	public ROOT lessThan(int value) {
		return addTest(i -> i < value, Integer.toString(value), "is less than");
	}

	public ROOT between(int start, int end) {
		return addTest(i -> i >= start && i <= end, "%s and %s".formatted(start, end), "between");
	}
}
