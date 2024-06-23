package com.rebeyka.acapi.entities;

import java.util.function.Predicate;

public class Trigger {

	private Predicate<Playable> condition;

	private Actionable actionable;

	private Playable playable;

	public Trigger(Predicate<Playable> condition, Actionable trigger) {
		this.condition = condition;
		this.actionable = trigger;
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

	public boolean test(Playable test) {
		System.out.println("Testing trigger against %s".formatted(test.getAttribute("value").getValue()));
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
