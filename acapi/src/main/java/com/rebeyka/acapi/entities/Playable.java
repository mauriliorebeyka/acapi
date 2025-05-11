package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.reflect.TypeToken;
import com.rebeyka.acapi.builders.PlayBuilder;
import com.rebeyka.acapi.exceptions.InvalidAttributeTypeException;

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

	public Set<String> getAttributeNames() {
		return attributes.keySet();
	}

	public Attribute<?> getAttribute(String name) {
		return attributes.get(name);
	}

	@SuppressWarnings("unchecked")
	public <T extends Comparable<? super T>> Attribute<T> getAttribute(String name, TypeToken<T> type) {
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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public String toString() {
		String id = "";
		if (getAttribute("name") != null && getAttribute("name").getValue() instanceof String value) {
			id = value;
		} else {
			id = getId();
		}
		return "%s - #%s".formatted(id, hashCode());
	}
}
