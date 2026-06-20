package com.rebeyka.acapi.entities.gameflow;

import java.util.ArrayList;
import java.util.List;

import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.Player;

public class RoundRobinGameFlow extends GameFlow {

	protected int currentPlayer;

	public RoundRobinGameFlow(GameFlowBuilder builder) {
		super(builder);
	}

	@Override
	public List<Player> getPlayersInOrder() {
		List<Player> playersInOrder = new ArrayList<>();
		for (int i = 0; i < players.size(); i++) {
			playersInOrder.add(players.get((firstPlayer + i) % players.size()));
		}
		return playersInOrder;
	}

	@Override
	public Player getCurrentPlayer() {
		if (allPlayersPassed()) {
			return game.NOBODY;
		} else {
			return players.get(currentPlayer);
		}
	}

	@Override
	public boolean isPlayerActive(Player player) {
		return player == getCurrentPlayer();
	}

	@Override
	public void nextTurn(boolean pass) {
		if (pass) {
			passedPlayers.add(getCurrentPlayer());
		}
		if (allPlayersPassed()) {
			return;
		}
		do {
			currentPlayer = (currentPlayer + 1) % players.size();
		} while (isPlayerPassed(getCurrentPlayer()));
	}

	@Override
	public void nextRound() {
		super.nextRound();
		currentPlayer = firstPlayer;
	}

}
