package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class GameCheck<BASE, ROOT extends AbstractCheck<?,?,BASE,?>> extends AbstractCheck<GameCheck<BASE,ROOT>, ROOT, BASE, Game> {

	protected GameCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Game> function) {
		super(root, testResults, function, game -> (Game) game);
	}
	
	@Override
	protected GameCheck<BASE,ROOT> self(List<TestResult<BASE>> testResults) {
		return new GameCheck<>(root, testResults, function);
	}

	public ROOT allPlayersPassed() {
		return addTest(g -> g.getGameFlow().allPlayersPassed(), "all players passed", "passed");
	}
}
