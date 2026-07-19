package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public abstract class ValueCheck<SELF extends ValueCheck<SELF, BASE, T, ROOT>, BASE, T, ROOT extends AbstractCheck<?, BASE, ?>>
		extends RootCheck<SELF, BASE, T, ROOT> {

	protected Function<T, ?> valueAcessor;
	
	public ValueCheck(ROOT root, Function<BASE,T> function, String testedField, Function<BASE,Game> gameAcessor) {
		super(root, function, testedField, gameAcessor);
	}
	
	public IntegerCheck<BASE, ROOT> asInt() {
		return new IntegerCheck<>(root,
				p -> (int) valueAcessor.apply(function.apply(p)),
				"integer attribute %s".formatted(testedField), gameAcessor);
	}

	public StringCheck<BASE, ROOT> asString() {
		return new StringCheck<BASE, ROOT>(root,
				p -> (String) valueAcessor.apply(function.apply(p)),
				"String attribute %s".formatted(testedField), gameAcessor);
	}
}
