package com.rebeyka.acapi.check;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class ValueCheck<SELF extends ValueCheck<SELF, BASE, T, ROOT>, BASE, T, ROOT extends AbstractCheck<?, BASE, ?>>
		extends AbstractCheck<SELF, BASE, T> {

	private static final Logger LOG = LogManager.getLogger();

	private ROOT root;

	protected String testedField;

	private Function<T, Object> sub;

	protected ValueCheck(ROOT root, Function<BASE, T> function, String testedField) {
		super(root.testResults, function);
		this.root = root;
		this.testedField = testedField;
	}

	@SuppressWarnings("unchecked")
	protected ROOT root() {
		try {
			return (ROOT) root.getClass().getDeclaredConstructor(List.class, Function.class).newInstance(testResults,
					root.function);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOG.warn("Class {} failed to create a new instance: {}", root.getClass(), e.getMessage());
			return root;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected SELF me(boolean newInstance) {
		if (newInstance) {
			try {
				return (SELF) this.getClass().getDeclaredConstructor(AbstractCheck.class, Function.class, String.class)
						.newInstance(this.root, this.function, this.testedField);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				LOG.warn("Failed to create new instance of %s".formatted(this.getClass()), e);
			}
		}
		return (SELF) this;
	}

	protected void addValueTest(String name, Predicate<T> predicate) {
		addTest(predicate, testedField, name);
	}

	public ROOT sameValue(T value) {
		addValueTest("is %s".formatted(value), s -> s.equals(value));
		return root();
	}
}
