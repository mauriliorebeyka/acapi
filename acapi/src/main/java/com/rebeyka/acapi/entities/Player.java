package com.rebeyka.acapi.entities;

import java.util.HashMap;
import java.util.Map;

public class Player extends Playable {

	private Map<String, Deck> decks;

	private boolean automatic;

	public Player(String id) {
		super(id);
		this.decks = new HashMap<>();
	}

	public Map<String, Deck> getDecks() {
		return decks;
	}

	public Deck getDeck(String name) {
		return decks.get(name);
	}

	public boolean isAutomatic() {
		return automatic;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

}
