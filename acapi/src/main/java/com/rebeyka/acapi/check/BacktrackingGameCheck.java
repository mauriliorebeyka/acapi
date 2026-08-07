package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class BacktrackingGameCheck<BASE, ROOT extends AbstractCheck<?,?,BASE,?>> extends AbstractCheck<BacktrackingGameCheck<BASE,ROOT>, ROOT, BASE, Game> {

	protected BacktrackingGameCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Game> function) {
		super(root, testResults, function, game -> (Game) game);
	}
	
	protected BacktrackingGameCheck(List<TestResult<BASE>> testResults, Function<BASE,Game> function) {
		super(testResults, function, game -> (Game) game);
	}
	
	@Override
	protected BacktrackingGameCheck<BASE,ROOT> self(List<TestResult<BASE>> testResults) {
		return new BacktrackingGameCheck<>(root, testResults, function);
	}

	public ROOT allPlayersPassed() {
		return addTest(g -> g.getGameFlow().allPlayersPassed(), "all players passed", "passed");
	}
}
