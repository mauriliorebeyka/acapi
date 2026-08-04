package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.random.Die;

public class DieCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>>
		extends ValueCheck<BASE, ROOT, Die<?>> {

	protected DieCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Die<?>> function,
			Function<BASE, Game> gameAcessor) {
		super(root, testResults, function, gameAcessor);
		this.valueAcessor = d -> d.getValue();
	}

	@Override
	protected DieCheck<BASE, ROOT> self(List<TestResult<BASE>> testResults) {
		return new DieCheck<>(root, testResults, function, gameAcessor);
	}

	public ROOT isRolled() {
		return addTest(p -> p.isRolled(), "is", "rolled");
	}

}
