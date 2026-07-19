package com.rebeyka.acapi.check;

import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.entities.Game;

public abstract class RootCheck<SELF extends RootCheck<SELF, BASE, T, ROOT>, BASE, T, ROOT extends AbstractCheck<?, BASE, ?>>
		extends AbstractCheck<SELF, BASE, T> {

	private static final Logger LOG = LogManager.getLogger();

	protected ROOT root;

	protected String testedField;

	protected RootCheck(ROOT root, Function<BASE, T> function, String testedField, Function<BASE, Game> gameAcessor) {
		super(root.testResults, function, gameAcessor);
		this.root = root;
		this.testedField = testedField;
	}

	//TODO should return a new instance of ROOT instead of the same, to keep the list of tests independent and allow the same checker to be used for different cases.
	protected ROOT addValueTest(Predicate<T> predicate, String name) {
		addTest(predicate, testedField, name);
		return root;
	}
	
	public ROOT sameValueAs(T other) {
		return addValueTest(p -> p.equals(other), "same value as");
	}
	
}
