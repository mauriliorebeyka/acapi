package com.rebeyka.acapi.actionables.gameflow;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.gameflow.NoPlayerGameFlow;

public class EndGameActionable extends Actionable {

	private Game game;
	
	public EndGameActionable(Game game) {
		super("END_GAME", null);
		this.game = game;
	}

	@Override
	public void execute() {
		game.setGameFlow(new NoPlayerGameFlow(new GameFlowBuilder(game)));
		game.end();
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMessage() {
		return "Ending game";
	}

	
}
