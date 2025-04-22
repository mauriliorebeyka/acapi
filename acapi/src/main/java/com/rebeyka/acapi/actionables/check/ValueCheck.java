package com.rebeyka.acapi.actionables.check;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class ValueCheck<BASE, T, S, ROOT extends AbstractCheck<BASE,T>> extends AbstractCheck<BASE,T>{

	private static final Logger LOG = LogManager.getLogger();
	
	private ROOT root;
	
	protected String testedField;
	
	protected Function<T, S> subFunction;
	
	protected ValueCheck(ROOT root, Function<T, S> subFunction, String testedField) {
		super(root.testResults, root.function);
		this.root = root;
		this.testedField = testedField;
		this.subFunction = subFunction;
	}
	
	@SuppressWarnings("unchecked")
	protected ROOT myself() {
		try {
			return (ROOT) root.getClass().getDeclaredConstructor(List.class, Function.class).newInstance(testResults, root.function);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOG.warn("Class {} failed to create a new instance: {}",root.getClass(), e.getMessage());
			return root;
		}
	}
	
	protected void addValueTest(String name, Predicate<S> predicate) {
		addTest(t -> predicate.test(subFunction.apply(t)), (Function<T, Object>) subFunction, testedField, name);
	}
	
	protected void addValueTest(String name, Predicate<S> predicate, Function<T, Object> subF) {
		addTest(t -> predicate.test(subFunction.apply(t)), subF, testedField, name);
	}
	
	public ROOT is(S value) {
		addValueTest("is %s".formatted(value), s -> s.equals(value));
		return myself();
	}
	
	ROOT notNull() {
		addValueTest("NOT NULL", Objects::nonNull);
		return myself();
	}
}
