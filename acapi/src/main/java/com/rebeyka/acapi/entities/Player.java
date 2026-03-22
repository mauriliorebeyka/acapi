package com.rebeyka.acapi.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.rebeyka.acapi.exceptions.GameElementNotFoundException;

public class Player extends Playable {

	private Map<String, PlayArea<? extends Collection<?>, ? extends BasePlayable>> playAreas;

	private boolean automatic;

	public Player(String id) {
		super(id);
		this.playAreas = new HashMap<>();
	}

	public Stream<PlayArea<? extends Collection<?>, ? extends BasePlayable>> getPlayAreas() {
		return playAreas.values().stream();
	}

	public Stream<BasePlayable> getAllPlayables() {
		return getPlayAreas().flatMap(PlayArea::getAllPlayables);
	}
	
	public Deck getDeck(String name) {
		if (!playAreas.containsKey(name)) {
			playAreas.put(name, new Deck(name, this));
		}

		return getPlayArea(name, Deck.class);
	}

	public PlayArea<? extends Collection<?>, ? extends BasePlayable> getPlayArea(String name) {
		if(!playAreas.containsKey(name)) {
			throw new GameElementNotFoundException("No Play Area defined with name %s".formatted(name));
		}
		return playAreas.get(name);
	}

	public <T extends PlayArea<? extends Collection<?>,?>> T getPlayArea(String name, Class<T> type) {
		PlayArea<? extends Collection<?>,?> playArea = getPlayArea(name);
		if (!type.isInstance(playArea)) {
			throw new IllegalStateException("Expected Play Area %s to be a %s, but it was %s instead".formatted(name,
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
