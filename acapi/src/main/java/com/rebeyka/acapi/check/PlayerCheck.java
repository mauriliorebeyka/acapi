package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Player;

public class PlayerCheck extends BacktrackingPlayerCheck<Player,PlayerCheck> implements RootChecker<Player, PlayerCheck>{

	protected PlayerCheck(PlayerCheck root, Function<Player, Player> function) {
		super(root, function);
	}

	protected PlayerCheck() {
		super(null, Function.identity());
	}
	
	@Override
	public PlayerCheck self() {
		return new PlayerCheck(this, function);
	}
}
