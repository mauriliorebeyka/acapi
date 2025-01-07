package com.rebeyka.acapi.entities.gameflow;

import java.util.Arrays;
import java.util.List;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.gameflow.GameFlow.FirstPlayerPolicy;

public class GameFlowBuilder {

	private Game game;

	private List<Player> players;

	private List<String> gamePhases;

	private FirstPlayerPolicy firstPlayerPolicy;

	private boolean staggerNewRound;

	public GameFlowBuilder() {
		this.game = null;
		this.players = null;
		this.gamePhases = Arrays.asList("MAIN");
		this.firstPlayerPolicy = FirstPlayerPolicy.SAME;
		this.staggerNewRound = false;
	}

	public GameFlowBuilder withGame(Game game) {
		this.game = game;
		return this;
	}

	public GameFlowBuilder withPlayers(List<Player> players) {
		this.players = players;
		return this;
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
}
