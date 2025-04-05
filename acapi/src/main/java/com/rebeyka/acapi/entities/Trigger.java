package com.rebeyka.acapi.entities;

import java.util.List;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.builders.PlayBuilder;

public class Trigger {

	private static final Logger LOG = LogManager.getLogger();

	private String triggerOnActionableId;

	private Predicate<Game> condition;

	private PlayBuilder playToTrigger;

	public Trigger(Predicate<Game> condition, PlayBuilder actionableToTrigger, String triggerOnActionableId) {
		this.condition = condition;
		this.playToTrigger = actionableToTrigger;
		this.triggerOnActionableId = triggerOnActionableId;
	}

	public Trigger(PlayBuilder trigger, String triggerOnActionableId) {
		this(_ -> true, trigger, triggerOnActionableId);
	}

	public Trigger(PlayBuilder trigger) {
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
		LOG.info("Testing trigger %s against %s".formatted(triggerOnActionableId, triggeringActionable.getActionableId()));
		boolean matchingId = triggerOnActionableId.equals("ALL") || triggerOnActionableId.equals(triggeringActionable.getActionableId());
		if (matchingId && condition.test(test.getGame())) {
			LOG.debug("Test passed, triggering play {}",playToTrigger.getId());
			playToTrigger.withOrigin(test.getOrigin());
			playToTrigger.withGame(test.getGame());
			return true;
		} else {
			return false;
		}
	}

	public Play getTriggeredPlay(List<Playable> targets) {
		Play play = new Play(playToTrigger);
		play.setTargets(targets);
		return play;
	}

	public void setActionableToTrigger(PlayBuilder actionableToTrigger) {
		this.playToTrigger = actionableToTrigger;
	}

}
