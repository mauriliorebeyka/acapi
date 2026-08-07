package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class GameCheck extends BacktrackingGameCheck<Game, GameCheck>{

	protected GameCheck(GameCheck root, List<TestResult<Game>> testResults, Function<Game, Game> function) {
		super(root, testResults, function);
	}

	protected GameCheck(List<TestResult<Game>> testResults, Function<Game, Game> function) {
		super(testResults, function);
	}
	
	@Override
	protected GameCheck self(List<TestResult<Game>> testResults) {
		return new GameCheck(root,testResults,function);
	}

}
