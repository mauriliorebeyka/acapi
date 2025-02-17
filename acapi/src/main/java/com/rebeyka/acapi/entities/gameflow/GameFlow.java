package com.rebeyka.acapi.entities.gameflow;

import java.util.List;
import java.util.Random;

import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public abstract class GameFlow {

	protected int firstPlayer;

	protected int currentGamePhase;

	protected Game game;

	protected List<Player> players;

	protected List<String> gamePhases;

	protected FirstPlayerPolicy firstPlayerPolicy;

	boolean staggerNewRound;

	private int round;

	public GameFlow(GameFlowBuilder builder) {
		if (builder.getGame() == null) {
			throw new IllegalArgumentException("Game cannot be null");
		}
		if (builder.getPlayers() == null || builder.getPlayers().isEmpty()) {
			throw new IllegalArgumentException("Players cannot be null");
		}
		if (builder.getGamePhases().isEmpty()) {
			throw new IllegalArgumentException("At least one game phase needed");
		}
		this.game = builder.getGame();
		this.players = builder.getPlayers();
		this.gamePhases = builder.getGamePhases();
		this.firstPlayerPolicy = builder.getFirstPlayerPolicy();
		this.firstPlayerPolicy.setPlayers(players);
		this.staggerNewRound = builder.isStaggerNewRound();

		this.round = 1;
	}

	public int getRound() {
		return round;
	}

	public void nextRound() {
		round++;
		firstPlayer = firstPlayerPolicy.getNewFirstPlayer();
	}

	public Player getFirstPlayer() {
		return getPlayersInOrder().get(0);
	}

	public String getCurrentGamePhase() {
		return this.gamePhases.get(currentGamePhase);
	}

	public abstract List<Player> getPlayersInOrder();

	public abstract Player getCurrentPlayer();

	public abstract boolean isPlayerActive(Player player);

	public boolean nextPhase() {
		if (this.currentGamePhase == this.gamePhases.size() - 1) {
			this.currentGamePhase = 0;
			return nextTurn();
		} else {
			this.currentGamePhase++;
			return false;
		}
	}

	public abstract boolean nextTurn();

}
