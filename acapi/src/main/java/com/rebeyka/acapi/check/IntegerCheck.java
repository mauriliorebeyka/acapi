package com.rebeyka.acapi.check;

import java.util.function.Function;

public class IntegerCheck extends BacktrackingIntegerCheck<Integer, IntegerCheck> implements RootChecker<Integer,IntegerCheck>{
	
	protected IntegerCheck(IntegerCheck root, Function<Integer, Integer> function) {
		super(root, function);
	}

	protected IntegerCheck() {
		super(null, Function.identity());
	}
	
	@Override
	public IntegerCheck self() {
		return new IntegerCheck(this, function);
	}
}
