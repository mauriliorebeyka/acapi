package com.rebeyka.acapi.builders;

import java.util.Arrays;
import java.util.List;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.gameflow.FirstPlayerPolicy;
import com.rebeyka.acapi.entities.gameflow.SamePlayerFirstPlayerPolicy;

public class GameFlowBuilder {

	private Game game;

	private List<Player> players;

	private List<String> gamePhases;

	private FirstPlayerPolicy firstPlayerPolicy;

	private boolean staggerNewRound;
	
	private int initialRound;

	public GameFlowBuilder(Game game) {
		this.game = game;
		this.players = game.getPlayers();
		this.gamePhases = Arrays.asList("MAIN");
		this.firstPlayerPolicy = new SamePlayerFirstPlayerPolicy();
		this.staggerNewRound = false;
		this.initialRound = 1;
	}

	public GameFlowBuilder withGamePhases(List<String> gamePhases) {
		this.gamePhases = gamePhases;
		return this;
	}

	public GameFlowBuilder withFirstPlayerPolicy(FirstPlayerPolicy firstPlayerPolicy) {
		this.firstPlayerPolicy = firstPlayerPolicy;
		return this;
	}

	public GameFlowBuilder withStaggerNewRound(boolean staggerNewRound) {
		this.staggerNewRound = staggerNewRound;
		return this;
	}

	public GameFlowBuilder withInitialRound(int initialRound) {
		this.initialRound = initialRound;
		return this;
	}
	
	public Game getGame() {
		return game;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<String> getGamePhases() {
		return gamePhases;
	}

	public FirstPlayerPolicy getFirstPlayerPolicy() {
		return firstPlayerPolicy;
	}

	public boolean isStaggerNewRound() {
		return staggerNewRound;
	}
	
	public int getInitialRound() {
		return initialRound;
	}
}
