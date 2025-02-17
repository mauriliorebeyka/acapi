package com.rebeyka.acapi.entities.gameflow;

import java.util.List;

import com.rebeyka.acapi.entities.Player;

public class SamePlayerFirstPlayerPolicy extends FirstPlayerPolicy {

	@Override
	public int getNewFirstPlayer() {
		return currentFirstPlayer;
	}

}
