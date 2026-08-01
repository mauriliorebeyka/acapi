package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class StringCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>>
		extends AbstractCheck<StringCheck<BASE, ROOT>, ROOT, BASE, String> {

	protected StringCheck(ROOT root, Function<BASE, String> function, Function<BASE, Game> gameAcessor) {
		super(root, function, gameAcessor);
	}

	protected StringCheck(List<TestResult<BASE>> testResults, Function<BASE, String> function,
			Function<BASE, Game> gameAcessor) {
		super(testResults, function, gameAcessor);
	}

	@Override
	protected StringCheck<BASE, ROOT> self() {
		return new StringCheck<>(root, function, gameAcessor);
	}

	public ROOT contains(String value) {
		return addTest(s -> s.contains(value), value, "contains");
	}

}
