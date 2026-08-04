package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;

public class PlayerCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>>
		extends AbstractCheck<PlayerCheck<BASE, ROOT>, ROOT, BASE, Player> {

	public PlayerCheck(List<TestResult<BASE>> testResults, Function<BASE, Player> function,
			Function<BASE, Game> gameAcessor, ROOT root) {
		super(root, testResults, function, gameAcessor);
	}

	protected PlayerCheck(List<TestResult<BASE>> testResults, Function<BASE, Player> function,
			Function<BASE, Game> gameAcessor) {
		super(testResults, function, gameAcessor);
	}

	@Override
	protected PlayerCheck<BASE, ROOT> self(List<TestResult<BASE>> testResults) {
		return new PlayerCheck<BASE, ROOT>(testResults, function, gameAcessor, root);
	}

	public StringCheck<BASE, ROOT> id() {
		return new StringCheck<>(root, testResults, f -> function.apply(f).getId(), gameAcessor);
	}

	public ROOT isCurrentPlayer() {
		return addTest(p -> p.getGame().getGameFlow().isCurrentPlayer(p), "is", "current player");
	}

	public ROOT isActivePlayer() {
		return addTest(p -> p.getGame().getGameFlow().isPlayerActive(p), "is", "active player");
	}

}
