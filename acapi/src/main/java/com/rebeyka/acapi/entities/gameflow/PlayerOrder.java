package com.rebeyka.acapi.entities.gameflow;

import java.util.List;
import java.util.Random;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public abstract class PlayerOrder {

	public static enum FirstPlayerPolicy {
		SAME, NEXT, RANDOM
	}

	protected int firstPlayer;

	protected Game game;

	protected List<Player> players;

	protected FirstPlayerPolicy firstPlayerPolicy;

	protected boolean staggerNewRound;

	private int round;

	public PlayerOrder(Game game, List<Player> players, boolean staggerNewRound, FirstPlayerPolicy firstPlayerPolicy) {
		this.game = game;
		this.players = players;
		round = 0;
		this.staggerNewRound = staggerNewRound;
		this.firstPlayerPolicy = firstPlayerPolicy;
		if (!staggerNewRound) {
			newRound();
		}
	}

	public int getRound() {
		return round;
	}

	public void newRound() {
		round++;
		if (staggerNewRound) {
			firstPlayer = getNewFirstPlayer();
		}
	}

	public Player getFirstPlayer() {
		return getPlayersInOrder().get(0);
	}

	public abstract List<Player> getPlayersInOrder();

	public abstract Player getCurrentPlayer();

	public abstract boolean isPlayerActive(Player player);

	public abstract boolean endTurn();

	protected int getNewFirstPlayer() {
		return switch (firstPlayerPolicy) {
		case NEXT -> ((firstPlayer + 1) % players.size());
		case RANDOM -> new Random(System.currentTimeMillis()).nextInt(players.size());
		case SAME -> firstPlayer;
		};
	}
}
