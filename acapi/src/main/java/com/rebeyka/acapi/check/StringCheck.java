package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

public class StringCheck extends BacktrackingStringCheck<String, StringCheck> {

	protected StringCheck(StringCheck root, List<TestResult<String>> testResults,
			Function<String, String> function) {
		super(root, testResults, function);
	}

	protected StringCheck(List<TestResult<String>> testResults, Function<String, String> function) {
		super(testResults, function);
	}
	
	@Override
	protected StringCheck self(List<TestResult<String>> testResults) {
		return new StringCheck(root, testResults, function);
	}
}
