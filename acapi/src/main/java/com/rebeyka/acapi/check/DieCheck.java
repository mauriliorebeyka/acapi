package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.random.Die;

public class DieCheck<BASE, ROOT extends AbstractCheck<?, BASE, ?>>
		extends ValueCheck<DieCheck<BASE, ROOT>, BASE, Die<?>, ROOT> {

	protected DieCheck(ROOT root, Function<BASE, Die<?>> function, String testedField,
			Function<BASE, Game> gameAcessor) {
		super(root, function, testedField, gameAcessor);
		this.valueAcessor = d -> d.getValue();
	}

	@Override
	protected DieCheck<BASE, ROOT> self() {
		return new DieCheck<>(root, function, testedField, gameAcessor);
	}

	public ROOT isRolled() {
		return addValueTest(p -> p.isRolled(), "is rolled");
	}

}
