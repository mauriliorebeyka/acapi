package com.rebeyka.acapi.entities;

import java.util.List;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;

public class Play {

	private Playable origin;

	private Cost cost;

	private Predicate<Game> condition;

	private List<Actionable> actionables;

	public Play(Playable origin, Cost cost, List<Actionable> actionables) {
		this.origin = origin;
		this.cost = cost;
		this.actionables = actionables;
		this.condition = i -> true;

		this.actionables.stream().forEach(a -> a.setParent(this));
		this.cost.generateActionable().setParent(this);
	}

	public Playable getOrigin() {
		return origin;
	}

	public void setOrigin(Playable origin) {
		this.origin = origin;
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
