package com.rebeyka.acapi.check;

import java.util.function.Function;

public class IntegerCheck<BASE, T, ROOT extends AbstractCheck<? extends AbstractCheck<?,BASE,T>,BASE,T>>
		extends ValueCheck<IntegerCheck<BASE,T,ROOT>, BASE, Integer, ROOT> {
	protected IntegerCheck(ROOT root, Function<BASE, Integer> function, String testedField) {
		super(root, function, testedField);
	}

	public ROOT biggerThan(int value) {
		addValueTest("is bigger than %s".formatted(value), s -> s > value);
		return root();
	}
}
