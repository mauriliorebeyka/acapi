package com.rebeyka.acapi.builders;

import java.util.ArrayList;
import java.util.List;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.gameflow.RoundRobinGameFlow;
import com.rebeyka.acapi.exceptions.WrongPlayerCountException;

public abstract class GameSetup {

	private String gameId;

	protected List<Player> players;

	private int minimumPlayers;

	private int maximumPlayers;

	private Game game;
	
	public GameSetup() {
		this(1, Integer.MAX_VALUE);
	}

	public GameSetup(int minimumPlayers, int maximumPlayers) {
		players = new ArrayList<>();
		this.minimumPlayers = minimumPlayers;
		this.maximumPlayers = maximumPlayers;
		this.gameId = "empty id";
	}

	public Game newGame() throws WrongPlayerCountException {
		if (players.size() < minimumPlayers || players.size() > maximumPlayers) {
			throw new WrongPlayerCountException("Wrong number of players");
		}
		game = new Game(gameId, players);
		game.setGameFlow(new RoundRobinGameFlow(new GameFlowBuilder(game)));

		players.stream().forEach(this::playerSetup);
		gameSetup(game);

		return game;
	}

	protected final Player nobody() {
		return game.NOBODY;
	}
	
	public void addPlayer(String playerName) {
		Player player = new Player(playerName);
		players.add(player);
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getGameId() {
		return gameId;
	}

	public abstract String getDescription();

	public abstract void playerSetup(Player player);

	public abstract void gameSetup(Game game);

}