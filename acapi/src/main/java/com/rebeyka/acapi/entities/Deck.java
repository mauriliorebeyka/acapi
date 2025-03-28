package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

	private String id;

	private List<Card> cards;

	public Deck(String id) {
		this.id = id;
		cards = new ArrayList<>();
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
	
	@Override
	public String toString() {
		return "Deck %s, containing %s cards".formatted(id, cards.size());
	}
}
