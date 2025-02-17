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
		if (currentPlayer == -1) {
			return null;
		} else {
			return players.get(currentPlayer);
		}
	}

	@Override
	public boolean isPlayerActive(Player player) {
		return player == getCurrentPlayer();
	}

	@Override
	public boolean nextTurn() {
		currentPlayer = (currentPlayer + 1) % players.size();
		if (getFirstPlayer() == getCurrentPlayer()) {
			if (staggerNewRound) {
				currentPlayer = -1;
			} else {
				nextRound();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void nextRound() {
		super.nextRound();
		currentPlayer = firstPlayer;
	}

}
