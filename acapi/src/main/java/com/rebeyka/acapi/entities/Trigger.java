package com.rebeyka.acapi.entities;

import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.actionables.Actionable;

public class Trigger {

	private static final Logger LOG = LogManager.getLogger();

	private String triggerOnActionableId;

	private Predicate<Game> condition;

	private Play playToTrigger;

	public Trigger(Predicate<Game> condition, Play actionableToTrigger, String triggerOnActionableId) {
		this.condition = condition;
		this.playToTrigger = actionableToTrigger;
		this.triggerOnActionableId = triggerOnActionableId;
	}

	public Trigger(Play trigger, String triggerOnActionableId) {
		this(_ -> true, trigger, triggerOnActionableId);
	}

	public Trigger(Play trigger) {
		this(trigger, "ALL");
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
		Play test = triggeringActionable.getParent();
		LOG.info("Testing trigger %s against %s".formatted(triggerOnActionableId,
				triggeringActionable.getActionableId()));
		boolean matchingId = triggerOnActionableId.equals("ALL")
				|| triggerOnActionableId.equals(triggeringActionable.getActionableId());
		if (matchingId && condition.test(test.getGame())) {
			LOG.debug("Test passed, triggering play {}", playToTrigger.getName());
			return true;
		} else {
			return false;
		}
	}

	public Play getTriggeredPlay(Play base) {
		Play play = new Play.Builder(playToTrigger).targets(base.getTargets())
				.game(base.getGame())
				.origin(base.getOrigin()).build();
		return play;
	}

}
