package com.rebeyka.acapi.entities;

public class Card extends Playable {

	public Card(String id) {
		super(id);
	}

	@Override
	public String toString() {
		if (getAttribute("name") != null && getAttribute("name").getValue() instanceof String value) {
			return value;
		}
		return "Card ID %s".formatted(getId());
	}

}
