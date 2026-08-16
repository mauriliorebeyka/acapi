package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;

public class GameCheck extends BacktrackingGameCheck<Game, GameCheck> implements RootChecker<Game,GameCheck>{

	protected GameCheck(GameCheck root, Function<Game, Game> function) {
		super(root, function);
	}

	protected GameCheck() {
		super(null, Function.identity());
	}
	
	@Override
	public GameCheck self() {
		return new GameCheck(root,function);
	}

}
