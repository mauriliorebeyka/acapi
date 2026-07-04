package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class StringCheck<BASE, T, ROOT extends AbstractCheck<?,BASE,T>>
extends ValueCheck<StringCheck<BASE, T, ROOT>, BASE, String, ROOT> {

	protected StringCheck(ROOT root, Function<BASE, String> function, String testedField, Function<BASE, Game> gameAcessor) {
		super(root, function, testedField, gameAcessor);
	}

	public ROOT contains(String value) {
		addValueTest("contains %s".formatted(value), s -> s.contains(value));
		return root();
	}

}
