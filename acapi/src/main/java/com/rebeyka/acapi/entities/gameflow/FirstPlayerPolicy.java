package com.rebeyka.acapi.entities.gameflow;

import java.util.List;

import com.rebeyka.acapi.entities.Player;

public class FirstPlayerPolicy {

	private int currentFirstPlayer;

	private List<Player> players;

	public FirstPlayerPolicy(List<Player> players) {
		this.players = players;
	}
}
