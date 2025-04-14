package com.rebeyka.acapi.actionables.check;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractCheck<BASE, T> {

	protected final Map<String, Predicate<BASE>> tests;
	
	protected Function<BASE, T> function;
	
	protected AbstractCheck(Map<String, Predicate<BASE>> tests, Function<BASE,T> function) {
		this.tests = tests;
		this.function = function;
	}
	
	public void addTest(String title, Predicate<T> predicate) {
		tests.put(title,t -> predicate.test(function.apply(t)));
	}
	
	public final boolean check(BASE actionable) {
		tests.forEach((k, v) -> {
			
		System.out.print(k+": ");
		System.out.println(v.test(actionable));
		});
		if (tests.isEmpty()) {
			return false;
		}
		return tests.values().stream().map(p -> p.test(actionable)).allMatch(b -> b == true);
	}
	
}
