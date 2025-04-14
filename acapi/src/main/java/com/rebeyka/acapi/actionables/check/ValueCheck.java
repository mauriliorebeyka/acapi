package com.rebeyka.acapi.actionables.check;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class ValueCheck<BASE, T, S, ROOT extends AbstractCheck<BASE,T>> extends AbstractCheck<BASE,T>{

	private ROOT root;
	
	protected String testedField;
	
	protected Function<T, S> subFunction;
	
	public ValueCheck(Map<String, Predicate<BASE>> tests, Function<T, S> subFunction, String testedField, ROOT root) {
		super(tests, root.function);
		this.root = root;
		this.testedField = testedField;
		this.subFunction = subFunction;
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
	
	protected void addValueTest(String name, Predicate<S> predicate) {
		addTest("%s %s".formatted(testedField,name), u -> predicate.test(subFunction.apply(u)));
	}
	
	public ROOT equalsTo(S value) {
		addValueTest("equals", s -> s.equals(value));
		return myself();
	}
	
	ROOT notNull() {
		addValueTest("NOT NULL", Objects::nonNull);
		return myself();
	}
}
