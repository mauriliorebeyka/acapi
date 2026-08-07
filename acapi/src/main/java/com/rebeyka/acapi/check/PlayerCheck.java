package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class PlayerCheck extends BacktrackingPlayerCheck<Player,PlayerCheck>{

	protected PlayerCheck(PlayerCheck root, List<TestResult<Player>> testResults, Function<Player, Player> function,
			Function<Player, Game> gameAcessor) {
		super(root, testResults, function, gameAcessor);
	}

	protected PlayerCheck(List<TestResult<Player>> testResults, Function<Player, Player> function,
			Function<Player, Game> gameAcessor) {
		super(testResults, function, gameAcessor);
	}
	
	@Override
	protected PlayerCheck self(List<TestResult<Player>> testResults) {
		return new PlayerCheck(this, testResults, function, gameAcessor);
	}
}
