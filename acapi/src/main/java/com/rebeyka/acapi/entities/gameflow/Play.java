package com.rebeyka.acapi.entities.gameflow;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.check.AbstractCheck;
import com.rebeyka.acapi.check.Checker;
import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;

public class Play {

	private String name;

	private Playable origin;

	private List<Playable> targets;

	private Game game;

	private Cost cost;

	private AbstractCheck<?,Playable,Playable> condition;

	private List<Supplier<Actionable>> actionables;

	private Trigger triggeredBy;
	
	private Play(Builder builder) {
		this.name = builder.name;
		this.origin = builder.origin;
		this.targets = builder.targets;
		this.game = builder.game;
		this.cost = builder.cost;
		this.condition = builder.condition;
		this.actionables = builder.actionables;
		this.triggeredBy = builder.triggeredBy;

		if (this.cost != null) {
			this.cost.getCostActionable().setParent(this);
		}
	}

	public String getName() {
		return name;
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
	public AbstractCheck<?,Playable,Playable> getCondition() {
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

	public Trigger getTriggeredBy() {
		return triggeredBy;
	}
	
	public Builder copy() {
		return new Builder(this);
	}
	
	public static final class Builder {
		
		private String name;

		private Playable origin;

		private List<Playable> targets;

		private Game game;

		private Cost cost;

		private AbstractCheck<?,Playable,Playable> condition;

		private List<Supplier<Actionable>> actionables;
		
		private Trigger triggeredBy;

		
		public Builder() {
			this.condition = Checker.whenPlayable().always();
			this.actionables = new ArrayList<>();
		}

		public Builder(Play copy) {
			this.name = copy.getName();
			this.origin = copy.getOrigin();
			this.targets = copy.getTargets();
			this.cost = copy.getCost();
			this.condition = copy.getCondition();
			this.actionables = copy.getActionableSuppliers();

			this.game = copy.getGame();
		}
		
		public Builder name(String id) {
			this.name = id;
			return this;
		}
		
		public Builder origin(Playable origin) {
			this.origin = origin;
			return this;
		}

		public Builder targets(List<Playable> targets) {
			this.targets = targets;
			return this;
		}
		
		public Builder target(Playable target) {
			return targets(List.of(target));
		}
		
		public Builder game(Game game) {
			this.game = game;
			return this;
		}
		
		public Builder cost(Cost cost) {
			this.cost = cost;
			return this;
		}
		
		public Builder condition(AbstractCheck<?,Playable,Playable> condition) {
			this.condition = condition;
			return this;
		}
		
		public Builder actionables(List<Supplier<Actionable>> actionables) {
			this.actionables = new ArrayList<Supplier<Actionable>>(actionables);
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Builder actionables(Supplier<Actionable>... actionables) {
			this.actionables = Stream.of(actionables).toList();
			return this;
		}
		
		public Builder actionable(Supplier<Actionable> actionable) {
			return actionables(List.of(actionable));
		}

		public Builder triggeredBy(Trigger trigger) {
			this.triggeredBy = trigger;
			return this;
		}
		
		public Play build() {
			if (name == null) {
				throw new InvalidParameterException("name cannot be null");
			}
			return new Play(this);
		}
	}
}
