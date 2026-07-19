package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class StringCheck<BASE, ROOT extends AbstractCheck<?, BASE, ?>>
		extends RootCheck<StringCheck<BASE, ROOT>, BASE, String, ROOT> {

	protected StringCheck(ROOT root, Function<BASE, String> function, String testedField,
			Function<BASE, Game> gameAcessor) {
		super(root, function, testedField, gameAcessor);
	}

	@Override
	protected StringCheck<BASE, ROOT> self() {
		return new StringCheck<>(root, function, testedField, gameAcessor);
	}

	public ROOT contains(String value) {
		return addValueTest(s -> s.contains(value), "contains %s".formatted(value));
	}

}
