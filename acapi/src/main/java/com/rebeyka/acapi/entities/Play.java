package com.rebeyka.acapi.entities;

import java.util.List;

public class Play {

	private Card origin;

	private Player owner;

	private Cost cost;

	private List<Actionable> actionables;

	public Play(Card origin, Player owner, List<Actionable> actionables) {
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

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

	public List<Actionable> getActionables() {
		return actionables;
	}

	public void setActionables(List<Actionable> actionables) {
		this.actionables = actionables;
	}

}
