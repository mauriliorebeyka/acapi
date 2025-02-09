package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Playable {

	private List<Play> plays;

	private Map<String, Attribute<?>> attributes;

	private Game game;

	public Playable() {
		plays = new ArrayList<>();
		attributes = new HashMap<>();
	}

	public List<Play> getPlays() {
		return plays;
	}

	public void setPlays(List<Play> plays) {
		this.plays = plays;
	}

	public Map<String, Attribute<?>> getAttributes() {
		return attributes;
	}

	public Attribute<?> getAttribute(String name) {
		return attributes.get(name);
	}

	public void setAttributes(Map<String, Attribute<?>> attributes) {
		this.attributes = attributes;
	}

	public void setAttribute(String name, Attribute<?> attribute) {
		attributes.put(name, attribute);
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
