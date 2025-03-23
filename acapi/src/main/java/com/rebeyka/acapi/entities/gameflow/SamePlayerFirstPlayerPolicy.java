package com.rebeyka.acapi.entities.gameflow;

public class SamePlayerFirstPlayerPolicy extends FirstPlayerPolicy {

	@Override
	public int getNewFirstPlayer() {
		return currentFirstPlayer;
	}

}
