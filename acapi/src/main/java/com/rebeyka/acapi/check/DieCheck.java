package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.random.Die;

public class DieCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>>
		extends ValueCheck<BASE, ROOT, Die<?>> {

	protected DieCheck(ROOT root, Function<BASE, Die<?>> function,
			Function<BASE, Game> gameAcessor) {
		super(root, function, gameAcessor);
		this.valueAcessor = d -> d.getValue();
	}

	@Override
	protected DieCheck<BASE, ROOT> self() {
		return new DieCheck<>(root, function, gameAcessor);
	}

	public ROOT isRolled() {
		return addTest(p -> p.isRolled(), "is", "rolled");
	}

}
