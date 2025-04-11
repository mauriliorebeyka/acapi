package com.rebeyka.acapi.actionables.check;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;
public abstract class TypedActionableCheck<T> {

	protected final Map<String, Predicate<Actionable>> tests;
	
	protected Function<Actionable, T> function;
	
	protected TypedActionableCheck(Map<String, Predicate<Actionable>> tests, Function<Actionable, T> function) {
		this.tests = tests;
		this.function = function;
	}
	
	public StringCheck<T, TypedActionableCheck<T>> id() {
		return new StringCheck<>(tests, Actionable::getActionableId, "Actionable ID", this);
	}
	
	public PlayableCheck origin() {
		return new PlayableCheck(tests, a -> a.getParent().getOrigin());
	}
	
	public final boolean check(Actionable actionable) {
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
