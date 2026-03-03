package com.rebeyka.acapi.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Player extends Playable {

	private Map<String, PlayArea<? extends Collection<?>, ? extends BasePlayable>> playAreas;

	private boolean automatic;

	public Player(String id) {
		super(id);
		this.playAreas = new HashMap<>();
	}

	public Set<String> getPlayAreaNames() {
		return playAreas.keySet();
	}

	public Deck getDeck(String name) {
		if (!playAreas.containsKey(name)) {
			playAreas.put(name, new Deck(name, this));
		}

		return getPlayArea(name, Deck.class);
	}

	public PlayArea<? extends Collection<?>, ? extends BasePlayable> getPlayArea(String name) {
		return playAreas.get(name);
	}

	public <T extends PlayArea<? extends Collection<?>,?>> T getPlayArea(String name, Class<T> type) {
		PlayArea<? extends Collection<?>,?> playArea = getPlayArea(name);
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

}
