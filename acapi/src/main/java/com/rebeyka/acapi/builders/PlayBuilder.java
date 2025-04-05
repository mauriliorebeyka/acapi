package com.rebeyka.acapi.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;

public class PlayBuilder {

	private String id;

	private Playable origin;

	private Game game;
	
	private Cost cost;

	private Predicate<Game> condition;

	private List<Supplier<Actionable>> actionables;
	
	public PlayBuilder() {
		this.condition = _ -> true;
		this.actionables = new ArrayList<>();
	}
	
	public PlayBuilder withId(String id) {
		this.id = id;
		return this;
	}
	
	public PlayBuilder withOrigin(Playable origin) {
		this.origin = origin;
		return this;
	}
	
	public PlayBuilder withGame(Game game) {
		this.game = game;
		return this;
	}
	
	public PlayBuilder withCost(Cost cost) {
		this.cost = cost;
		return this;
	}
	
	public PlayBuilder withCondition(Predicate<Game> condition) {
		this.condition = condition;
		return this;
	}
	
	public PlayBuilder withActionables(List<Supplier<Actionable>> actionables) {
		this.actionables = new ArrayList<Supplier<Actionable>>(actionables);
		return this;
	}
	
	public PlayBuilder addActionable(Supplier<Actionable> actionable) {
		this.actionables.add(actionable);
		return this;
	}
	
	public PlayBuilder clearActionables() {
		this.actionables.clear();
		return this;
	}

	public String getId() {
		return id;
	}

	public Playable getOrigin() {
		return origin;
	}

	public Game getGame() {
		return game;
	}
	
	public Cost getCost() {
		return cost;
	}

	public Predicate<Game> getCondition() {
		return condition;
	}

	public List<Supplier<Actionable>> getActionables() {
		return actionables;
	}
}
