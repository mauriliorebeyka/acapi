package com.rebeyka.acapi.entities;

import java.util.List;
import java.util.function.Predicate;

public class Play {

	private Card origin;

	private Player owner;

	private Cost cost;

	private Predicate<Game> condition;

	private List<Actionable> actionables;

	public Play(Card origin, Player owner, List<Actionable> actionables) {
		this.origin = origin;
		this.owner = owner;
		this.actionables = actionables;
		this.condition = i -> true;

		this.actionables.stream().forEach(a -> a.setParent(this));
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

	public void setCondition(Predicate<Game> condition) {
		this.condition = condition;
	}

	public Predicate<Game> getCondition() {
		return condition;
	}

	public List<Actionable> getActionables() {
		return actionables;
	}

	public void setActionables(List<Actionable> actionables) {
		this.actionables = actionables;
	}

}
