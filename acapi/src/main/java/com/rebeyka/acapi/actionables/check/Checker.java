package com.rebeyka.acapi.actionables.check;

public class Checker{
	
	public static ActionableChecker whenActionable() {
		return new ActionableChecker();
	}
	
	public static PlayableChecker whenPlayable() {
		return new PlayableChecker();
	}
}

