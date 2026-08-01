package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public abstract class ValueCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>, T>
		extends AbstractCheck<ValueCheck<BASE,ROOT,T>, ROOT, BASE, T> {

	protected Function<T, ?> valueAcessor;

	public ValueCheck(ROOT root, Function<BASE, T> function, Function<BASE, Game> gameAcessor) {
		super(root, function, gameAcessor);
	}

	public IntegerCheck<BASE, ROOT> asInt() {
		return new IntegerCheck<>(root, p -> (int) valueAcessor.apply(function.apply(p)), gameAcessor);
	}

	public StringCheck<BASE, ROOT> asString() {
		return new StringCheck<BASE, ROOT>(root, p -> (String) valueAcessor.apply(function.apply(p)),gameAcessor);
	}
}
