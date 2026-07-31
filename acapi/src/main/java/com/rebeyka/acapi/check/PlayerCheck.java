package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;

public class PlayerCheck<BASE> extends AbstractCheck<PlayerCheck<BASE>, BASE, Player> {

	private Function<BASE, Playable> originalFunction;
	
	protected PlayerCheck(List<TestResult<BASE>> testResults, Function<BASE, Player> function,
			Function<BASE, Game> gameAcessor, Function<BASE, Playable> originalFunction) {
		super(testResults, function, gameAcessor);
		this.originalFunction = originalFunction;
	}

	@Override
	protected PlayerCheck<BASE> self() {
		return new PlayerCheck<BASE>(testResults, function, gameAcessor, originalFunction);
	}

	protected PlayableCheck<BASE> addTestPlayable(Predicate<Player> p, String field, String description) {
		addTest(p, field, description);
		return new PlayableCheck<BASE>(testResults, originalFunction);
	}

	public StringCheck<BASE, PlayableCheck<BASE>> id() {
		return new StringCheck<BASE, PlayableCheck<BASE>>(new PlayableCheck<BASE>(testResults, originalFunction), f -> function.apply(f).getId(), "ID", gameAcessor);
	}

	public PlayableCheck<BASE> isCurrentPlayer() {
		return addTestPlayable(p -> p.getGame().getGameFlow().isCurrentPlayer(p), "is", "current player");
	}
	
	public PlayableCheck<BASE> isActivePlayer() {
		return addTestPlayable(p -> p.getGame().getGameFlow().isPlayerActive(p), "is", "active player");
	}
	
}
