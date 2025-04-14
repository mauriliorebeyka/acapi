package com.rebeyka.acapi.actionables.check;

import java.util.HashMap;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Playable;

public class Checker{

	
	public static ActionableCheck<Actionable> whenActionable() {
		return new ActionableCheck<>(new HashMap<>(), p -> p);
	}
	
	public static PlayableCheck<Playable> whenPlayable() {
		return new PlayableCheck<Playable>(new HashMap<>(), p -> p);
	}
}
