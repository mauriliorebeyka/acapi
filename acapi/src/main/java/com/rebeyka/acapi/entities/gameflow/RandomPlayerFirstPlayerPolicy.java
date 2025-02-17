package com.rebeyka.acapi.entities.gameflow;

import com.rebeyka.acapi.random.Seed;

public class RandomPlayerFirstPlayerPolicy extends FirstPlayerPolicy {

	@Override
	public int getNewFirstPlayer() {
		currentFirstPlayer = Seed.randomInt(getPlayers().size());
		return currentFirstPlayer;
	}

}
