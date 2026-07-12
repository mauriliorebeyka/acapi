package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class IntegerCheck<BASE, ROOT extends AbstractCheck<?,BASE,?>>
		extends ValueCheck<IntegerCheck<BASE,ROOT>, BASE, Integer, ROOT> {

	protected IntegerCheck(ROOT root, Function<BASE, Integer> function, String testedField, Function<BASE, Game> gameAcessor) {
		super(root, function, testedField, gameAcessor);
	}

	public ROOT biggerThan(int value) {
		addValueTest("is bigger than %s".formatted(value), s -> s > value);
		return root;
	}

	@Override
	protected IntegerCheck<BASE, ROOT> self(boolean newInstance) {
		if (newInstance) {
			return new IntegerCheck<>(root, function, testedField, gameAcessor);
		}
		return this;
	}
}
