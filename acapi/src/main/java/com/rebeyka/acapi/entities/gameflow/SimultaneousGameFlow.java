package com.rebeyka.acapi.entities.gameflow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.Player;

public class SimultaneousGameFlow extends GameFlow {

	public SimultaneousGameFlow(GameFlowBuilder builder) {
		super(builder);
	}

	@Override
	public List<Player> getPlayersInOrder() {
		return players;
	}

	@Override
	public Player getCurrentPlayer() {
		if (allPlayersPassed()) {
			return game.NOBODY;
		} else {
			return players.get(firstPlayer);
		}
	}

	@Override
	public boolean isPlayerActive(Player player) {
		return !isPlayerPassed(player);
	}

	@Override
	public void nextTurn(boolean pass) {
			
	}

	public boolean endTurn(Player player) {
		passedPlayers.add(player);
		return allPlayersPassed();
	}


}
