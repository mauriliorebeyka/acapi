package com.rebeyka.acapi.actionables.check;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;

public abstract class ValueCheck<T, A, ROOT extends TypedActionableCheck<A>> extends TypedActionableCheck<T>{

	private ROOT root;
	
	public ValueCheck(Map<String, Predicate<Actionable>> tests, Function<Actionable, T> function, ROOT root) {
		super(tests, function);
		this.root = root;
	}
	
	@SuppressWarnings("unchecked")
	protected ROOT myself() {
		try {
			return (ROOT) root.getClass().getDeclaredConstructor(Map.class, Function.class).newInstance(tests, root.function);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			return root;
		}
	}
	
	public ROOT equalsTo(T value) {
		tests.put("equals", p -> function.apply(p).equals(value));
		return myself();
	}
	
	ROOT notNull() {
		tests.put("NOT NULL", p -> function.apply(p) != null);
		return myself();
	}
}
