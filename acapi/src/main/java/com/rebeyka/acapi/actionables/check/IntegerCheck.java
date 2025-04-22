package com.rebeyka.acapi.actionables.check;

import java.util.function.Function;

public class IntegerCheck<BASE, T, ROOT extends AbstractCheck<BASE,T>> extends ValueCheck<BASE, T, Integer, ROOT> {

	protected IntegerCheck(ROOT root, Function<T, Integer> function, String testedField) {
		super(root, function, testedField);
	}

	public ROOT biggerThan(int value) {
		addValueTest("is bigger than %s".formatted(value), s -> s > value);
		return myself();
	}
}
