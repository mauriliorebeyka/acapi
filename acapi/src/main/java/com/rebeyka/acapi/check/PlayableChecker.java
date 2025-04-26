package com.rebeyka.acapi.check;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Playable;

public class PlayableChecker extends PlayableCheck<Playable>{

	protected PlayableChecker() {
		super(new ArrayList<TestResult<Playable>>(), Function.identity());
	}

	protected PlayableChecker(List<TestResult<Playable>> results, Function<Playable, Playable> function) {
		super(results, function);
	}
	
}
