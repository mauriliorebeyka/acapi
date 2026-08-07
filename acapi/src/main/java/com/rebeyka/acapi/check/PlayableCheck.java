package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Playable;

public class PlayableCheck extends BacktrackingPlayableCheck<Playable, PlayableCheck>{

	protected PlayableCheck(PlayableCheck root, List<TestResult<Playable>> testResults, Function<Playable, Playable> function) {
		super(root, testResults, function);
	}
	
	protected PlayableCheck(List<TestResult<Playable>> testResults, Function<Playable, Playable> function) {
		super(testResults, function);
	}

	@Override
	protected PlayableCheck self(List<TestResult<Playable>> testResults) {
		return new PlayableCheck(this, testResults, function);
	}
}
