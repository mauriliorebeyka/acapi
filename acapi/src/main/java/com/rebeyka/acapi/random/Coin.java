package com.rebeyka.acapi.random;

import java.util.List;
import java.util.Random;

public class Coin extends Die<CoinSides> {

	public Coin(List<DieFace<CoinSides>> faces, Random seed) {
		super(faces, seed);
	}

	public Coin flip() {
		roll();
		return this;
	}

	public boolean isHeads() {
		return getValue().equals(CoinSides.HEADS);
	}

	public boolean isTails() {
		return getValue().equals(CoinSides.TAILS);
	}
}
