package com.rebeyka.acapi.actionables.gameflow;

import java.util.function.Supplier;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.gameflow.NoPlayerGameFlow;

public class EndGameActionable extends Actionable {

	public EndGameActionable() {
		super("END_GAME");
	}

	@Override
	public void execute() {
		Game game = getParent().getGame();
		game.setGameFlow(
				new NoPlayerGameFlow(new GameFlowBuilder(game).withInitialRound(game.getGameFlow().getRound())));
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

	@Override
	public Supplier<Actionable> supply() {
		return () -> new EndGameActionable();
	}
}
