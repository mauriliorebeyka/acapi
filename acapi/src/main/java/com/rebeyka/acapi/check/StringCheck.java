package com.rebeyka.acapi.check;

import java.util.function.Function;

public class StringCheck extends BacktrackingStringCheck<String, StringCheck>
		implements RootChecker<String, StringCheck> {

	protected StringCheck(StringCheck root, Function<String, String> function) {
		super(root, function);
	}

	protected StringCheck() {
		super(null, Function.identity());
	}

	@Override
	public StringCheck self() {
		return new StringCheck(root, function);
	}
}
