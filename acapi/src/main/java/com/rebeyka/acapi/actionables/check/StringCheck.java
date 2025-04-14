package com.rebeyka.acapi.actionables.check;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class StringCheck<BASE, T, ROOT extends AbstractCheck<BASE, T>> extends ValueCheck<BASE,T,String,ROOT> {

	protected StringCheck(Map<String, Predicate<BASE>> tests, Function<T, String> function, String testedField, ROOT root) {
		super(tests, function, testedField, root);
	}

	public ROOT contains(String value) {
		addValueTest("contains", s -> s.contains(value));
		return myself();
	}
	
}
