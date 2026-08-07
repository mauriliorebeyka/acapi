package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class IntegerCheck extends BacktrackingIntegerCheck<Integer, IntegerCheck>{
	
	protected IntegerCheck(IntegerCheck root, List<TestResult<Integer>> testResults, Function<Integer, Integer> function, Function<Integer, Game> gameAcessor) {
		super(root, testResults, function, gameAcessor);
	}

	protected IntegerCheck(List<TestResult<Integer>> testResults, Function<Integer, Integer> function, Function<Integer, Game> gameAcessor) {
		super(testResults, function, gameAcessor);
	}
	
	@Override
	protected IntegerCheck self(List<TestResult<Integer>> testResults) {
		return new IntegerCheck(this, testResults, function, gameAcessor);
	}
}
