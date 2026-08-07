package com.rebeyka.acapi.check;

import java.util.ArrayList;
import java.util.function.Function;

public class Checker{
	
	public static GameCheck whenGame() {
		return new GameCheck(new ArrayList<>(), Function.identity());
	}
	
	public static ActionableCheck whenActionable() {
		return new ActionableCheck(new ArrayList<>(), Function.identity());
	}
	
	public static PlayableCheck whenPlayable() {
		return new PlayableCheck(new ArrayList<>(), Function.identity());
	}	
	
	public static StringCheck whenString() {
		return new StringCheck(new ArrayList<>(), Function.identity(), null);
	}
	
	public static IntegerCheck whenInteger() {
		return new IntegerCheck(new ArrayList<>(), Function.identity(), null);
	}
	
	public static PlayerCheck whenPlayer() {
		return new PlayerCheck(new ArrayList<>(), Function.identity(), p -> p.getGame());
	}
}

