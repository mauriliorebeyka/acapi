package com.rebeyka.acapi.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player extends Playable {

	private Map<String, PlayArea> playAreas;

	private boolean automatic;

	public Player(String id) {
		super(id, null);
		this.playAreas = new HashMap<>();
	}

	public Set<String> getPlayAreaNames() {
		return playAreas.keySet();
	}

	public Deck getDeck(String name) {
		if (!playAreas.containsKey(name)) {
			playAreas.put(name, new Deck(name, getOwner()));
		}

		return getPlayArea(name, Deck.class);
	}

	public PlayArea getPlayArea(String name) {
		return playAreas.get(name);
	}

	public <T extends PlayArea> T getPlayArea(String name, Class<T> type) {
		PlayArea playArea = getPlayArea(name);
		if (!type.isInstance(playArea)) {
			throw new IllegalStateException("Expected %s to be a %s, but it was %s instead".formatted(name,
					type.getSimpleName(), playArea.getClass().getSimpleName()));
		}
		return type.cast(playArea);
	}

	public boolean isAutomatic() {
		return automatic;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

	@Override
	public Player getOwner() {
		return this;
	}

}
