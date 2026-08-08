package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Player;

public class BacktrackingPlayerCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>>
		extends AbstractCheck<BacktrackingPlayerCheck<BASE, ROOT>, ROOT, BASE, Player> {

	public BacktrackingPlayerCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Player> function) {
		super(root, testResults, function);
	}

	protected BacktrackingPlayerCheck(List<TestResult<BASE>> testResults, Function<BASE, Player> function) {
		super(testResults, function);
	}

	@Override
	protected BacktrackingPlayerCheck<BASE, ROOT> self(List<TestResult<BASE>> testResults) {
		return new BacktrackingPlayerCheck<BASE, ROOT>(root, testResults, function);
	}

	public BacktrackingStringCheck<BASE, ROOT> id() {
		return new BacktrackingStringCheck<>(root, testResults, f -> function.apply(f).getId());
	}

	public ROOT isCurrentPlayer() {
		return addTest(p -> p.getGame().getGameFlow().isCurrentPlayer(p), "is", "current player");
	}

	public ROOT isActivePlayer() {
		return addTest(p -> p.getGame().getGameFlow().isPlayerActive(p), "is", "active player");
	}

}
