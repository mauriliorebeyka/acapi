package com.rebeyka.acapi.entities;

import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;

public class Trigger {

	private String actionableId;

	private Predicate<Playable> condition;

	private Actionable actionable;

	private Playable playable;

	public Trigger(Predicate<Playable> condition, Actionable trigger) {
		this.condition = condition;
		this.actionable = trigger;
	}

	public Trigger(Predicate<Playable> condition) {
		this(condition, null);
	}

	public Trigger(Actionable trigger) {
		this(i -> true, trigger);
	}

	public Predicate<Playable> getCondition() {
		return condition;
	}

	public void setCondition(Predicate<Playable> condition) {
		this.condition = condition;
	}

	public boolean test(Actionable actionable) {
		Playable test = actionable.getPlayable();
		if (test == null) {
			return false;
		}
		System.out.println("Testing trigger against %s".formatted(test));
		if (condition.test(test)) {
			playable = test;
			return true;
		} else {
			return false;
		}
	}

	public Actionable getActionable() {
		return actionable;
	}

	public void setActionable(Actionable trigger) {
		this.actionable = trigger;
	}

	public Playable getPlayable() {
		return playable;
	}
}
