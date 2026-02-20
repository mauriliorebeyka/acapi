package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rebeyka.acapi.view.VisibilityType;

public class Deck extends PlayArea {

	public Deck(String id, Player owner) {
		super(id,owner);
		playables = new ArrayList<Playable>();
		visibilityType = VisibilityType.PUBLIC;
	}

	public void shuffle() {
		Collections.shuffle((List<Playable>)playables);
	}

	public Card draw() {
		return (Card)((List<Playable>)playables).removeFirst();
	}

	public void add(Card card) {
		((List<Playable>)playables).add(card);
	}

}
