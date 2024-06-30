package com.rebeyka.acapi.entities.gameflow;

import java.util.ArrayList;
import java.util.List;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class RoundRobinPlayerOrder extends PlayerOrder {

	protected int currentPlayer;

	public RoundRobinPlayerOrder(Game game, List<Player> players, boolean staggerNewRound,
			PlayerOrder.FirstPlayerPolicy firstPlayerPolicy) {
		super(game, players, staggerNewRound, firstPlayerPolicy);
	}

	public RoundRobinPlayerOrder(Game game, List<Player> players) {
		this(game, players, false, PlayerOrder.FirstPlayerPolicy.SAME);
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
	public boolean endTurn() {
		currentPlayer = (currentPlayer + 1) % players.size();
		if (getFirstPlayer() == getCurrentPlayer()) {
			if (staggerNewRound) {
				currentPlayer = -1;
			} else {
				newRound();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void newRound() {
		super.newRound();
		if (staggerNewRound) {
			currentPlayer = firstPlayer;
		}
	}

}
