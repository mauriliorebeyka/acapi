package com.rebeyka.acapi.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player extends Playable {

	private Map<String, Deck> decks;

	private boolean automatic;

	public Player(String id) {
		super(id);
		this.decks = new HashMap<>();
	}

	public Set<String> getDeckNames() {
		return decks.keySet();
	}
	
	public Deck getDeck(String name) {
		if (!decks.containsKey(name)) {
			decks.put(name, new Deck(name));
		}
		return decks.get(name);
	}

	public boolean isAutomatic() {
		return automatic;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

}
