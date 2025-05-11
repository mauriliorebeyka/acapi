package com.rebeyka.acapi.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.rebeyka.acapi.actionables.Actionable;

public class Play {

	private String id;

	private Playable origin;

	private List<Playable> targets;

	private Game game;

	private Cost cost;

	private Predicate<Game> condition;

	private List<Supplier<Actionable>> actionables;

	private Play(Builder builder) {
		this.id = builder.id;
		this.origin = builder.origin;
		this.targets = builder.targets;
		this.game = builder.game;
		this.cost = builder.cost;
		this.condition = builder.condition;
		this.actionables = builder.actionables;

		if (this.cost != null) {
			this.cost.getCostActionable().setParent(this);
		}
	}

	public String getId() {
		return id;
	}

	public Playable getOrigin() {
		return origin;
	}

	public List<Playable> getTargets() {
		return targets;
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

	public List<Actionable> getActionables() {
		return actionables.stream().map(this::enrich).collect(Collectors.toList());
	}

	private Actionable enrich(Supplier<Actionable> supplier) {
		Actionable actionable = supplier.get();
		actionable.setParent(this);
		return actionable;
	}
	
	public List<Supplier<Actionable>> getActionableSuppliers() {
		return actionables;
	}

	public static final class Builder {
		
		private String id;

		private Playable origin;

		private List<Playable> targets;

		private Game game;

		private Cost cost;

		private Predicate<Game> condition;

		private List<Supplier<Actionable>> actionables;

		
		public Builder() {
			this.condition = _ -> true;
			this.actionables = new ArrayList<>();
		}

		public Builder(Play copy) {
			this.id = copy.getId() + " " + UUID.randomUUID().toString();
			this.origin = copy.getOrigin();
			this.targets = copy.getTargets();
			this.cost = copy.getCost();
			this.condition = copy.getCondition();
			this.actionables = copy.getActionableSuppliers();

			this.game = copy.getGame();
		}
		
		public Builder withId(String id) {
			this.id = id;
			return this;
		}
		
		public Builder withOrigin(Playable origin) {
			this.origin = origin;
			return this;
		}

		public Builder withTargets(List<Playable> targets) {
			this.targets = targets;
			return this;
		}
		
		public Builder withTarget(Playable target) {
			return withTargets(List.of(target));
		}
		
		public Builder withGame(Game game) {
			this.game = game;
			return this;
		}
		
		public Builder withCost(Cost cost) {
			this.cost = cost;
			return this;
		}
		
		public Builder withCondition(Predicate<Game> condition) {
			this.condition = condition;
			return this;
		}
		
		public Builder withActionables(List<Supplier<Actionable>> actionables) {
			this.actionables = new ArrayList<Supplier<Actionable>>(actionables);
			return this;
		}
		
		public Builder withActionable(Supplier<Actionable> actionable) {
			return withActionables(List.of(actionable));
		}

		public Play build() {
			return new Play(this);
		}
	}
}
