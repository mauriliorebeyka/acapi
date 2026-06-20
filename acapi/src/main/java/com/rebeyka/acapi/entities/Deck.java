package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.rebeyka.acapi.random.Seed;
import com.rebeyka.acapi.view.VisibilityType;

public class Deck extends PlayArea<List<Card>,Card> {

	private Seed seed;
	
	public Deck(String id, Player owner) {
		this(id, owner, new Seed());
	}

	public Deck(String id, Player owner, Seed seed) {
		super(id,owner);
		playables = new ArrayList<Card>();
		visibilityType = VisibilityType.PUBLIC;
		this.seed = seed;
	}
	
	public void shuffle() {
		Collections.shuffle(getAll(), new Random(seed.nextLong()));
	}

	public void reshuffle() {
		seed.rollback();
		shuffle();
	}
	
	public Card draw() {
		return getAll().removeFirst();
	}

	public Card drawFromBottom() {
		return getAll().removeLast();
	}

}
