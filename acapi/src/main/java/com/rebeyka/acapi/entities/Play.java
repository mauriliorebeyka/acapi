package com.rebeyka.acapi.entities;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.builders.PlayBuilder;

public class Play {

	private String id;

	private Playable origin;

	private Cost cost;

	private Predicate<Game> condition;

	private List<Actionable> actionables;

	public Play(PlayBuilder builder) {
		this.id = builder.getId();
		this.origin = builder.getOrigin();
		this.cost = builder.getCost();
		this.condition = builder.getCondition();
		this.actionables = builder.getActionables();

		this.actionables.stream().forEach(a -> a.setParent(this));
		this.cost.generateActionable().setParent(this);
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
