package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rebeyka.acapi.view.VisibilityType;

public class Deck {

	private String id;

	private List<Card> cards;

	private VisibilityType visibilityType;
	
	private Player owner;
	
	public Deck(String id, Player owner) {
		this.id = id;
		cards = new ArrayList<>();
		visibilityType = VisibilityType.PUBLIC;
		this.owner = owner;
	}

	public void shuffle() {
		Collections.shuffle(cards);
	}

	public Card draw() {
		return cards.removeFirst();
	}

	public void add(Card card) {
		cards.add(card);
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public String getId() {
		return id;
	}
	
	public VisibilityType getVisibilityType() {
		return visibilityType;
	}

	public void setVisibilityType(VisibilityType visibilityType) {
		if (visibilityType.equals(VisibilityType.INHERIT)) {
			throw new IllegalArgumentException("Deck cannot inherit visibility");
		}
		this.visibilityType = visibilityType;
	}

	public Player getOwner() {
		return owner;
	}
	
	@Override
	public String toString() {
		return "Deck %s, containing %s cards".formatted(id, cards.size());
	}
}
