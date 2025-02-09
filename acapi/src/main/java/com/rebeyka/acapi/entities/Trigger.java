package com.rebeyka.acapi.entities;

import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.actionables.Actionable;

public class Trigger {

	private static final Logger LOG = LogManager.getLogger();

	private String triggerOnActionableId;

	private Predicate<Game> condition;

	private Actionable actionableToTrigger;

	private Playable playable;

	public Trigger(Predicate<Game> condition, Actionable actionableToTrigger, String triggerOnActionableId) {
		this.condition = condition;
		this.actionableToTrigger = actionableToTrigger;
		this.triggerOnActionableId = triggerOnActionableId;
	}

	public Trigger(Actionable trigger) {
		this(i -> true, trigger, "ALL");
	}

	public String getTriggerOnActionableId() {
		return triggerOnActionableId;
	}

	public void setTriggerOnActionableId(String triggerOnActionableId) {
		this.triggerOnActionableId = triggerOnActionableId;
	}

	public Predicate<Game> getCondition() {
		return condition;
	}

	public void setCondition(Predicate<Game> condition) {
		this.condition = condition;
	}

	public boolean test(Actionable triggeringActionable) {
		Playable test = triggeringActionable.getPlayable();
		if (test == null) {
			return false;
		}
		LOG.info("Testing trigger %s against %s".formatted(triggerOnActionableId, triggeringActionable.getActionableId()));
		boolean matchingId = triggerOnActionableId.equals("ALL") || triggerOnActionableId.equals(triggeringActionable.getActionableId());
		if (matchingId && condition.test(test.getGame())) {
			LOG.debug("Test passed");
			playable = test;
			return true;
		} else {
			return false;
		}
	}

	public Actionable getActionableToTrigger() {
		return actionableToTrigger;
	}

	public void setActionableToTrigger(Actionable actionableToTrigger) {
		this.actionableToTrigger = actionableToTrigger;
	}

	public Playable getPlayable() {
		return playable;
	}
}
