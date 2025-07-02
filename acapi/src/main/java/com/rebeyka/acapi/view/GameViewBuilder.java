package com.rebeyka.acapi.view;

import java.util.List;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class GameViewBuilder {

	public static GameView buildView(Game game, Player pov) {
		GameView gameView = new GameView();

		gameView.getAttributeView().add(new ValueView<Integer>("Round", game.getGameFlow().getRound()));
		gameView.getAttributeView().add(new ValueView<Player>("Current Player", game.getGameFlow().getCurrentPlayer()));
		gameView.getAttributeView().add(new ValueView<Player>("FirstPlayer", game.getGameFlow().getFirstPlayer()));
		gameView.getAttributeView().add(new ValueView<List<Player>>("Active Players", game.getGameFlow().getActivePlayers()));

		game.getPlayers().forEach(p -> gameView.getPlayerView().add(buildPlayerView(p)));
		
		return gameView;
	}
	
	private static PlayerView buildPlayerView(Player p) {
		PlayerView playerView = new PlayerView();
		
		return playerView;
	}
}
