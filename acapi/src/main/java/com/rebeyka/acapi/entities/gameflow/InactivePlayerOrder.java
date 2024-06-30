package com.rebeyka.acapi.entities.gameflow;

import java.util.List;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class InactivePlayerOrder extends PlayerOrder {

	public InactivePlayerOrder(Game game, List<Player> players) {
		super(game, players, false, FirstPlayerPolicy.SAME);
	}

	@Override
	public List<Player> getPlayersInOrder() {
		return players;
	}

	@Override
	public Player getCurrentPlayer() {
		return null;
	}

	@Override
	public boolean isPlayerActive(Player player) {
		return false;
	}

	@Override
	public boolean endTurn() {
		return false;
	}

}
