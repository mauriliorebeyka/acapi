package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class GameCheck<BASE, ROOT extends AbstractCheck<?,?,BASE,?>> extends AbstractCheck<GameCheck<BASE,ROOT>, ROOT, BASE, Game> {

	protected GameCheck(ROOT root, Function<BASE, Game> function) {
		super(root, function, game -> (Game) game);
	}
	
	@Override
	protected GameCheck<BASE,ROOT> self() {
		return new GameCheck<>(root, this.function);
	}

	public ROOT allPlayersPassed() {
		return addTest(g -> g.getGameFlow().allPlayersPassed(), "all players passed", "passed");
	}
}
