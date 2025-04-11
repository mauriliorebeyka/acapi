package com.rebeyka.acapi.actionables.check;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;

public class StringCheck<A, ROOT extends TypedActionableCheck<A>> extends ValueCheck<String, A,ROOT> {

	private String testedField;
	
	protected StringCheck(Map<String, Predicate<Actionable>> tests, Function<Actionable, String> function, String testedField, ROOT root) {
		super(tests, function, root);
		this.testedField = testedField;
	}

	public ROOT contains(String value) {
		tests.put("contains",p -> function.apply(p).contains(value));
		return myself();
	}
	
}
