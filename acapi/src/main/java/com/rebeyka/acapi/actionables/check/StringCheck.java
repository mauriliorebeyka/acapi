package com.rebeyka.acapi.actionables.check;

import java.util.function.Function;

public class StringCheck<BASE, T, ROOT extends AbstractCheck<BASE, T>> extends ValueCheck<BASE,T,String,ROOT> {

	protected StringCheck(ROOT root, Function<T, String> function, String testedField) {
		super(root, function, testedField);
	}

	public ROOT contains(String value) {
		addValueTest("contains %s".formatted(value), s -> s.contains(value));
		return myself();
	}
	
}
