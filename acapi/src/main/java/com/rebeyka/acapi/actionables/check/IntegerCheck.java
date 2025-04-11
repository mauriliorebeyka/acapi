package com.rebeyka.acapi.actionables.check;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;

public class IntegerCheck<A, ROOT extends TypedActionableCheck<A>> extends ValueCheck<Integer, A, ROOT> {

	public IntegerCheck(Map<String, Predicate<Actionable>> tests, Function<Actionable, Integer> function, ROOT root) {
		super(tests, function, root);
	}

	public ROOT biggerThan(int value) {
		tests.put("bigger than", p -> function.apply(p) > value);
		return myself();
	}
}
