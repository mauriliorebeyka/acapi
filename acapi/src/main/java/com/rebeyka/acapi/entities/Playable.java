package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.reflect.TypeToken;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.exceptions.InvalidAttributeTypeException;
import com.rebeyka.acapi.view.VisibilityType;

public abstract class Playable {

	private String id;

	private List<Play> plays;

	private Map<String, Attribute<?>> attributes;

	private Game game;
	
	private VisibilityType defaultVisibility;
	
	private Map<Player,VisibilityType> visibilityByPlayer;
	
	private Player owner;

	public Playable(String id, Player owner) {
		this.id = id;
		plays = new ArrayList<>();
		attributes = new HashMap<>();
		defaultVisibility = VisibilityType.INHERIT;
		visibilityByPlayer = new HashMap<>();
		this.owner = owner;
	}

	public String getId() {
		return id;
	}

	public List<Play> getPlays() {
		return plays;
	}

	public void setPlays(List<Play> plays) {
		this.plays = plays;
	}

	public Set<String> getAttributes() {
		return attributes.keySet();
	}

	public Attribute<?> getRawAttribute(String name) {
		return attributes.get(name);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Comparable<? super T>> Attribute<T> getRawAttribute(String name, TypeToken<T> type) {
		if (!attributes.containsKey(name)) {
			attributes.put(name, new Attribute<T>(name, type));
		}
		Attribute<?> attribute = attributes.get(name);
		if (attribute.getValue() == null || attribute.getType().equals(type)) {
			return (Attribute<T>) attribute;
		}
		throw new InvalidAttributeTypeException("Expected attribute type to be %s, but was %s instead"
				.formatted(type.getType(), attribute.getType().getType()));
	}
	
	public <T extends Comparable<? super T>> Attribute<T> getAttribute(String name, TypeToken<T> type) {
		return game.getModifiedAttribute(this, getRawAttribute(name, type));
	}

	public <T extends Comparable<? super T>> void setAttribute(String name, TypeToken<T> type, T value) {
		getRawAttribute(name, type).setValue(value);
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public VisibilityType getDefaultVisibility() {
		return defaultVisibility;
	}
	
	public void setDefaultVisibility(VisibilityType visibilityType) {
		this.defaultVisibility = visibilityType;
	}
	
	public void setVisibilityForPlayer(Player player, VisibilityType visibility) {
		visibilityByPlayer.put(player, visibility);
	}
	
	public VisibilityType getvisibilityForPlayer(Player player) {
		return visibilityByPlayer.getOrDefault(player, defaultVisibility);
	}
	
	public Player getOwner() {
		return owner;
	}
	
	@Override
	public String toString() {
		String id = "";
		if (getRawAttribute("name") != null && getRawAttribute("name").getValue() instanceof String value) {
			id = value;
		} else {
			id = getId();
		}
		return "%s - #%s".formatted(id, hashCode());
	}
}
