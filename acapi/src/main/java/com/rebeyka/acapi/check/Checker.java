package com.rebeyka.acapi.check;

import java.util.ArrayList;
import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;

public class Checker{
	
	public static ActionableCheck<Actionable, ActionableCheck<Actionable, ?>> whenActionable() {
		ActionableCheck<Actionable, ActionableCheck<Actionable, ?>> actionableCheck = new ActionableCheck<>(new ArrayList<TestResult<Actionable>>(), Function.identity());
		actionableCheck.root = actionableCheck;
		return actionableCheck;
	}
	
	public static PlayableCheck<Playable, PlayableCheck<Playable,?>> whenPlayable() {
		PlayableCheck<Playable, PlayableCheck<Playable,?>> playableCheck =  new PlayableCheck<>(new ArrayList<TestResult<Playable>>(), Function.identity());
		playableCheck.root = playableCheck;
		return playableCheck;
	}	
	
	public static StringCheck<String, StringCheck<String, ?>> whenString() {
		StringCheck<String, StringCheck<String,?>> stringCheck = new StringCheck<>(new ArrayList<>(), Function.identity(), null);
		stringCheck.root = stringCheck;
		return stringCheck;
	}
	
	public static IntegerCheck<Integer, IntegerCheck<Integer, ?>> whenInteger() {
		IntegerCheck<Integer, IntegerCheck<Integer,?>> integerCheck = new IntegerCheck<>(new ArrayList<>(), Function.identity(), null);
		integerCheck.root = integerCheck;
		return integerCheck;
	}
	
	public static PlayerCheck<Player, PlayerCheck<Player,?>> whenPlayer() {
		PlayerCheck<Player, PlayerCheck<Player,?>> playerCheck = new PlayerCheck<>(new ArrayList<>(), Function.identity(), p -> p.getGame());
		playerCheck.root = playerCheck;
		return playerCheck;
	}
}

