package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Game;

public abstract class WinningCondition extends Actionable {

	protected Game game;
	
	public WinningCondition(Game game) {
		super("WINNING CONDITION");
		this.game = game;
	}

	@Override
	public void rollback() {
		return;
	}

}
