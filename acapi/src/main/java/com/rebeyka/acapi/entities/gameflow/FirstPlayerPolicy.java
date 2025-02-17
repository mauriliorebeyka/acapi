package com.rebeyka.acapi.entities.gameflow;

import java.util.List;

import com.rebeyka.acapi.entities.Player;

public abstract class FirstPlayerPolicy {

	protected int currentFirstPlayer;

	private List<Player> players;
	
	public abstract int getNewFirstPlayer();

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
