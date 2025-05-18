package com.rebeyka.acapi.check;

import java.util.ArrayList;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Playable;

public class Checker{
	
	public static ActionableCheck<Actionable> whenActionable() {
		return new ActionableCheck<>(new ArrayList<TestResult<Actionable>>(), Function.identity());
	}
	
	public static PlayableCheck<Playable> whenPlayable() {
		return new PlayableCheck<>(new ArrayList<TestResult<Playable>>(), Function.identity());
	}
}

