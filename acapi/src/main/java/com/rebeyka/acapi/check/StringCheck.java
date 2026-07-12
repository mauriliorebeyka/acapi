package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class StringCheck<BASE, ROOT extends AbstractCheck<?,BASE,?>>
extends ValueCheck<StringCheck<BASE, ROOT>, BASE, String, ROOT> {

	protected StringCheck(ROOT root, Function<BASE, String> function, String testedField, Function<BASE, Game> gameAcessor) {
		super(root, function, testedField, gameAcessor);
	}

	@Override
	protected StringCheck<BASE, ROOT> self(boolean newInstance) {
		if (newInstance) {
			return new StringCheck<>(root, function, testedField, gameAcessor);
		}
		return this;
	}

	public ROOT contains(String value) {
		addValueTest("contains %s".formatted(value), s -> s.contains(value));
		return root;
	}

}
