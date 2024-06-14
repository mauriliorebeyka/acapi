package com.rebeyka.acapi.entities;

import java.util.List;

public class Script {

	private Card origin;

	private Player owner;

	private List<Actionable> actionables;

	public Script(Card origin, Player owner, List<Actionable> actionables) {
		this.origin = origin;
		this.owner = owner;
		this.actionables = actionables;
	}

	public Card getOrigin() {
		return origin;
	}

	public void setOrigin(Card origin) {
		this.origin = origin;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public List<Actionable> getActionables() {
		return actionables;
	}

	public void setActionables(List<Actionable> actionables) {
		this.actionables = actionables;
	}
}
