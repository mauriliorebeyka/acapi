package com.rebeyka.acapi.entities.gameflow;

import java.util.List;

import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.Player;

public class NoPlayerGameFlow extends GameFlow {

	public NoPlayerGameFlow(GameFlowBuilder builder) {
		super(builder.withGamePhases(List.of("NONE")));
	}

	@Override
	public List<Player> getPlayersInOrder() {
		return players;
	}

	@Override
	public Player getCurrentPlayer() {
		return game.NOBODY;
	}

	@Override
	public boolean isPlayerActive(Player player) {
		return false;
	}

	@Override
	public void nextTurn(boolean pass) {
		//EMPTY
	}

}
