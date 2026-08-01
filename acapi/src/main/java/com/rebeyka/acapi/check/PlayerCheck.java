package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;

public class PlayerCheck<BASE, ROOT extends AbstractCheck<?,BASE,?>> extends RootCheck<PlayerCheck<BASE,ROOT>, BASE, Player, ROOT> {

	private Function<BASE, Playable> originalFunction;
	
		public PlayerCheck(List<TestResult<BASE>> testResults, Function<BASE, Player> function, Function<BASE, Game> gameAcessor,
				Function<BASE, Playable> originalFunction, ROOT root) {
		super(root, function, "", gameAcessor);
		this.originalFunction = originalFunction;
	}
	
	protected PlayerCheck(List<TestResult<BASE>> testResults, Function<BASE, Player> function,
			Function<BASE, Game> gameAcessor, Function<BASE, Playable> originalFunction) {
		super(testResults, function, "", gameAcessor);
		this.originalFunction = originalFunction;
	}


	@Override
	protected PlayerCheck<BASE,ROOT> self() {
		return new PlayerCheck<BASE,ROOT>(testResults, function, gameAcessor, originalFunction);
	}

	public StringCheck<BASE, ROOT> id() {
		return new StringCheck<BASE, ROOT>(root, f -> function.apply(f).getId(), "ID", gameAcessor);
	}

	public ROOT isCurrentPlayer() {
		return addValueTest(p -> p.getGame().getGameFlow().isCurrentPlayer(p), "current player");
	}
	
	public ROOT isActivePlayer() {
		return addValueTest(p -> p.getGame().getGameFlow().isPlayerActive(p), "active player");
	}
	
}
