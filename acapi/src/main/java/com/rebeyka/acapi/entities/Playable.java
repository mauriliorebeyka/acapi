package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rebeyka.acapi.builders.PlayBuilder;

public abstract class Playable {

	private String id;

	private List<PlayBuilder> plays;
	
	private Map<String, Attribute<?>> attributes;

	private Game game;

	public Playable(String id) {
		this.id = id;
		plays = new ArrayList<>();
		attributes = new HashMap<>();
	}

	public String getId() {
		return id;
	}
	
	public List<PlayBuilder> getPlays() {
		return plays;
	}

	public void setPlays(List<PlayBuilder> plays) {
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
