package com.rebeyka.acapi.check;

import java.util.ArrayList;
import java.util.function.Function;

public class Checker{
	
	public static GameCheck whenGame() {
		return new GameCheck();
	}
	
	public static ActionableCheck whenActionable() {
		return new ActionableCheck();
	}
	
	public static PlayableCheck whenPlayable() {
		return new PlayableCheck();
	}	
	
	public static StringCheck whenString() {
		return new StringCheck();
	}
	
	public static IntegerCheck whenInteger() {
		return new IntegerCheck();
	}
	
	public static PlayerCheck whenPlayer() {
		return new PlayerCheck();
	}
}

