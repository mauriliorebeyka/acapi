package com.rebeyka.acapi.entities;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.builders.PlayBuilder;

public class Play {

	private String id;

	private Playable origin;

	private List<Playable> targets;
	
	private Game game;
	
	private Cost cost;

	private Predicate<Game> condition;

	private List<Supplier<Actionable>> actionables;

	public Play(PlayBuilder builder) {
		if (builder.getOrigin() == null) {
			throw new IllegalArgumentException("Cannot have null origin");
		}
		
		this.id = builder.getId();
		this.origin = builder.getOrigin();
		this.cost = builder.getCost();
		if (this.cost == null) {
			this.cost = new FreeCost(origin);
		}
		this.condition = builder.getCondition();
		this.actionables = builder.getActionables();

		this.cost.getCostActionable().setParent(this);
	}

	public Play(Play copy) {
		this.id = copy.getId() + " " + UUID.randomUUID().toString();
		this.origin = copy.getOrigin();
		this.cost = copy.getCost();
		if (this.cost == null) {
			this.cost = new FreeCost(origin);
		}
		this.condition = copy.getCondition();
		this.actionables = copy.getActionableSuppliers();

		this.cost.getCostActionable().setParent(this);
		
		this.game = copy.getGame();
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

	public List<Playable> getTargets() {
		return targets;
	}

	public void setTargets(List<Playable> targets) {
		this.targets = targets;
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
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
		return actionables.stream().map(Supplier::get).collect(Collectors.toList());
	}

	public List<Supplier<Actionable>> getActionableSuppliers() {
		return actionables;
	}
	
}
