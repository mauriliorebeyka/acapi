package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Playable;

public class PlayableCheck extends BacktrackingPlayableCheck<Playable, PlayableCheck> implements RootChecker<Playable,PlayableCheck>{

	protected PlayableCheck(PlayableCheck root, Function<Playable, Playable> function) {
		super(root, function);
	}
	
	protected PlayableCheck() {
		super(null, Function.identity());
	}

	@Override
	public PlayableCheck self() {
		return new PlayableCheck(this, function);
	}
}
