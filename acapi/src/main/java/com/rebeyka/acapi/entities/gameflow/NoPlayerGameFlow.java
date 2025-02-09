package com.rebeyka.acapi.entities.gameflow;

import java.util.List;

import org.assertj.core.util.Arrays;

import com.rebeyka.acapi.entities.Player;

public class NoPlayerGameFlow extends GameFlow {

	public NoPlayerGameFlow(GameFlowBuilder builder) {
		super(builder.withGamePhases(List.of("TEST")));
	}

	@Override
	public List<Player> getPlayersInOrder() {
		return players;
	}

	@Override
	public Player getCurrentPlayer() {
		return null;
	}

	@Override
	public boolean isPlayerActive(Player player) {
		return false;
	}

	@Override
	public boolean nextTurn() {
		return true;
	}

}
