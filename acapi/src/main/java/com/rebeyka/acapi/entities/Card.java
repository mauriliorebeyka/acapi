package com.rebeyka.acapi.entities;

public class Card extends Playable {

	public Card(String id, Player owner) {
		super(id, owner);
	}

	@Override
	public String toString() {
		if (getRawAttribute("name") != null && getRawAttribute("name").getValue() instanceof String value) {
			return value;
		}
		return "Card ID %s".formatted(getId());
	}

}
