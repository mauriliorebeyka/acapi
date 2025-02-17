package com.rebeyka.acapi.builders;

import java.util.ArrayList;
import java.util.List;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.gameflow.RoundRobinGameFlow;

public abstract class GameSetup {

	protected List<Player> players;
	
	public GameSetup() {
		players = new ArrayList<>();
	}
	
	public Game newGame() {
		Game game = new Game(players);
		game.setGameFlow(new RoundRobinGameFlow(new GameFlowBuilder(game)));
//		game.setGameEndActionable(new DeclareWinnerActionable("END_GAME", game));

		players.stream().forEach(p -> p.setPlays(createPlays(game,p)));
		createCommonTriggers(game);

		return game;
	}
	
	public void addPlayer(String playerName) {
		Player player = new Player();
		player.setAttribute("name", new Attribute<String>(playerName));
		createDefaultAttributes(player);
		players.add(player);
	}
	
	public abstract void createDefaultAttributes(Player player);
	
	public abstract List<Play> createPlays(Game game, Player player);
	
	public abstract void createCommonTriggers(Game game);
}
