package com.rebeyka.acapi.entities;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;

public class Play {

	private String id;

	private Playable origin;

	private Cost cost;

	private Predicate<Game> condition;

	private List<Actionable> actionables;

	public Play(String id, Playable origin, Cost cost, Predicate<Game> condition, List<Actionable> actionables) {
		this.id = id;
		this.origin = origin;
		this.cost = cost;
		this.condition = condition;
		this.actionables = actionables;

		this.actionables.stream().forEach(a -> a.setParent(this));
		this.cost.generateActionable().setParent(this);
	}

	public Play(String id, Playable origin, Actionable actionable) {
		this(id, origin, Cost.FREE, i -> true, Arrays.asList(actionable));
	}

	public String getId() {
		return id;
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
