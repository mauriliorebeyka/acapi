package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

public class IntegerCheck extends BacktrackingIntegerCheck<Integer, IntegerCheck>{
	
	protected IntegerCheck(IntegerCheck root, List<TestResult<Integer>> testResults, Function<Integer, Integer> function) {
		super(root, testResults, function);
	}

	protected IntegerCheck(List<TestResult<Integer>> testResults, Function<Integer, Integer> function) {
		super(testResults, function);
	}
	
	@Override
	protected IntegerCheck self(List<TestResult<Integer>> testResults) {
		return new IntegerCheck(this, testResults, function);
	}
}
