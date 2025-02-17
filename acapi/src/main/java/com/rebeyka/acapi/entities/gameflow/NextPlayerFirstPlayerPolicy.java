package com.rebeyka.acapi.entities.gameflow;

public class NextPlayerFirstPlayerPolicy extends FirstPlayerPolicy {

	@Override
	public int getNewFirstPlayer() {
		currentFirstPlayer = ((currentFirstPlayer + 1) % getPlayers().size());
		return currentFirstPlayer;
	}
}
