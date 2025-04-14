package com.rebeyka.acapi.actionables.check;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class IntegerCheck<BASE, T, ROOT extends AbstractCheck<BASE,T>> extends ValueCheck<BASE, T, Integer, ROOT> {

	public IntegerCheck(Map<String, Predicate<BASE>> tests, Function<T, Integer> function, String testedField, ROOT root) {
		super(tests, function, testedField, root);
	}

	public ROOT biggerThan(int value) {
		addValueTest("bigger than", s -> s > value);
		return myself();
	}
}
