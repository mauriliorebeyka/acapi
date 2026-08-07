package com.rebeyka.acapi.entities.gameflow;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.check.Checkable;
import com.rebeyka.acapi.check.Checker;
import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.GameEntity;
import com.rebeyka.acapi.entities.Playable;

public class Play extends GameEntity {

	private Playable origin;

	private List<Playable> targets;

	private Cost cost;

	private Checkable<Playable> condition;

	private List<Actionable> actionables;

	private Trigger triggeredBy;
	
	private Play(Builder builder) {
		super(builder.id);
		this.origin = builder.origin;
		this.targets = builder.targets;
		this.cost = builder.cost;
		this.condition = builder.condition;
		this.actionables = builder.actionables;
		this.triggeredBy = builder.triggeredBy;
		setGame(builder.game);

	}

	public Playable getOrigin() {
		return origin;
	}

	public List<Playable> getTargets() {
		return targets;
	}
	
	public Cost getCost() {
		return cost;
	}
	public Checkable<Playable> getCondition() {
		return condition;
	}

	public List<Actionable> getActionables() {
		return actionables.stream().map(actionable -> actionable.copy(this)).collect(Collectors.toList());
	}

	
	public List<Actionable> getActionableTemplates() {
		return actionables;
	}

	public Trigger getTriggeredBy() {
		return triggeredBy;
	}

	public boolean isPossible() {
		return getCondition().check(origin);
	}
	
	public Builder copy() {
		return new Builder(this);
	}
	
	public static class Builder {
		
		private String id;

		private Playable origin;

		private List<Playable> targets;

		private Game game;

		private Cost cost;

		private Checkable<Playable> condition;

		private List<Actionable> actionables;
		
		private Trigger triggeredBy;

		
		public Builder() {
			this.condition = Checker.whenPlayable().always();
			this.actionables = new ArrayList<>();
		}

		public Builder(Play copy) {
			this.id = copy.getId();
			this.origin = copy.getOrigin();
			this.targets = copy.getTargets();
			this.cost = copy.getCost();
			this.condition = copy.getCondition();
			this.actionables = copy.getActionableTemplates();

			this.game = copy.getGame();
		}
		
		public Builder id(String id) {
			this.id = id;
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
		
		public Builder condition(Checkable<Playable> condition) {
			this.condition = condition;
			return this;
		}
		
		public Builder actionables(List<Actionable> actionables) {
			this.actionables = new ArrayList<Actionable>(actionables);
			return this;
		}
		
		public Builder actionables(Actionable... actionables) {
			this.actionables = Stream.of(actionables).toList();
			return this;
		}
		
		public Builder actionable(Actionable actionable) {
			return actionables(List.of(actionable));
		}

		public Builder triggeredBy(Trigger trigger) {
			this.triggeredBy = trigger;
			return this;
		}
		
		public Play build() {
			if (id == null) {
				throw new InvalidParameterException("name cannot be null");
			}
			return new Play(this);
		}
	}
}
