package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class IntegerCheck<BASE, ROOT extends AbstractCheck<?, BASE, ?>>
		extends RootCheck<IntegerCheck<BASE, ROOT>, BASE, Integer, ROOT> {

	protected IntegerCheck(ROOT root, Function<BASE, Integer> function, String testedField,
			Function<BASE, Game> gameAcessor) {
		super(root, function, testedField, gameAcessor);
	}
	
	@Override
	protected IntegerCheck<BASE, ROOT> self() {
		return new IntegerCheck<>(root, function, testedField, gameAcessor);
	}

	public ROOT biggerThan(int value) {
		return addValueTest(i -> i > value, "is bigger than %s".formatted(value));
	}

	public ROOT lessThan(int value) {
		return addValueTest(i -> i < value, "is less than %s".formatted(value));
	}

	public ROOT between(int start, int end) {
		return addValueTest(i -> i >= start && i <= end, "between %s and %s".formatted(start, end));
	}
}
