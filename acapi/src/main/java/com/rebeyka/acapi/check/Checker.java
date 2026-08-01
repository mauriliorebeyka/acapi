package com.rebeyka.acapi.check;

import java.util.ArrayList;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;

public class Checker{
	
	public static ActionableCheck<Actionable, ActionableCheck<Actionable, ?>> whenActionable() {
		return new ActionableCheck<>(new ArrayList<TestResult<Actionable>>(), Function.identity());
	}
	
	public static PlayableCheck<Playable, PlayableCheck<Playable,?>> whenPlayable() {
		return new PlayableCheck<>(new ArrayList<TestResult<Playable>>(), Function.identity());
	}	
	
	public static StringCheck<String, StringCheck<String, ?>> whenString() {
		return new StringCheck<>(new ArrayList<>(), Function.identity(), null);
	}
	
	public static IntegerCheck<Integer, IntegerCheck<Integer, ?>> whenInteger() {
		return new IntegerCheck<>(new ArrayList<>(), Function.identity(), null);
	}
	
	public static PlayerCheck<Player, PlayerCheck<Player,?>> whenPlayer() {
		return new PlayerCheck<>(new ArrayList<>(), Function.identity(), p -> p.getGame());
	}
}

