package com.rebeyka.acapi.check;

import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.entities.Game;

public abstract class ValueCheck<SELF extends ValueCheck<SELF, BASE, T, ROOT>, BASE, T, ROOT extends AbstractCheck<?, BASE, ?>>
		extends AbstractCheck<SELF, BASE, T> {

	private static final Logger LOG = LogManager.getLogger();

	protected ROOT root;

	protected String testedField;

	protected ValueCheck(ROOT root, Function<BASE, T> function, String testedField, Function<BASE, Game> gameAcessor) {
		super(root.testResults, function, gameAcessor);
		this.root = root;
		this.testedField = testedField;
	}

	protected void addValueTest(String name, Predicate<T> predicate) {
		addTest(predicate, testedField, name);
	}
	
	public ROOT isEqualsTo(T value) {
		addValueTest("is %s".formatted(value), s -> s.equals(value));
		return root;
	}
}
