package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class GameCheck<BASE> extends AbstractCheck<GameCheck<BASE>, BASE, Game> {

	protected GameCheck(List<TestResult<BASE>> testResults, Function<BASE, Game> function) {
		super(testResults, function);
	}

	public GameCheck<BASE> allPlayersPassed() {
		addTest(g -> g.getGameFlow().allPlayersPassed(),"all players passed","passed");
		return me();
	}
}
