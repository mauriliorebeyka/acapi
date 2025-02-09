package com.rebeyka.acapi.entities;

import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.actionables.Actionable;

public class Trigger {

	private static final Logger LOG = LogManager.getLogger();

	private String actionableId;

	private Predicate<Playable> condition;

	private Actionable actionable;

	private Playable playable;

	public Trigger(Predicate<Playable> condition, Actionable trigger, String actionableId) {
		this.condition = condition;
		this.actionable = trigger;
		this.actionableId = actionableId;
	}

	public Trigger(Predicate<Playable> condition) {
		this(condition, null, "ALL");
	}

	public Trigger(Actionable trigger) {
		this(i -> true, trigger, "ALL");
	}

	public String getActionableId() {
		return actionableId;
	}

	public void setActionableId(String actionableId) {
		this.actionableId = actionableId;
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
		LOG.info("Testing trigger %s against %s".formatted(actionableId, actionable.getActionableId()));
		boolean matchingId = actionableId.equals("ALL") || actionableId.equals(actionable.getActionableId());
		if (matchingId && condition.test(test)) {
			LOG.debug("Test passed");
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
