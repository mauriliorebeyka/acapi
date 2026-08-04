package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class IntegerCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>>
		extends AbstractCheck<IntegerCheck<BASE, ROOT>, ROOT, BASE, Integer> {

	protected IntegerCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Integer> function, Function<BASE, Game> gameAcessor) {
		super(root, testResults, function, gameAcessor);
	}

	protected IntegerCheck(List<TestResult<BASE>> testResults, Function<BASE, Integer> function,
			Function<BASE, Game> gameAcessor) {
		super(testResults, function, gameAcessor);
	}

	@Override
	protected IntegerCheck<BASE, ROOT> self(List<TestResult<BASE>> testResults) {
		return new IntegerCheck<>(root, testResults, function, gameAcessor);
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
