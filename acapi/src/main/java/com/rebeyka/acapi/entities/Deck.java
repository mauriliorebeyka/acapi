package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rebeyka.acapi.view.VisibilityType;

public class Deck extends PlayArea<List<Card>,Card> {

	public Deck(String id, Player owner) {
		super(id,owner);
		playables = new ArrayList<Card>();
		visibilityType = VisibilityType.PUBLIC;
	}

	public void shuffle() {
		Collections.shuffle(getAll());
	}

	public Card draw() {
		return getAll().removeFirst();
	}

	public Card drawFromBottom() {
		return getAll().removeLast();
	}

}
