package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class StringCheck extends BacktrackingStringCheck<String, StringCheck> {

	protected StringCheck(StringCheck root, List<TestResult<String>> testResults,
			Function<String, String> function, Function<String, Game> gameAcessor) {
		super(root, testResults, function, gameAcessor);
	}

	protected StringCheck(List<TestResult<String>> testResults, Function<String, String> function,
			Function<String, Game> gameAcessor) {
		super(testResults, function, gameAcessor);
	}
	
	@Override
	protected StringCheck self(List<TestResult<String>> testResults) {
		return new StringCheck(root, testResults, function, gameAcessor);
	}
}
