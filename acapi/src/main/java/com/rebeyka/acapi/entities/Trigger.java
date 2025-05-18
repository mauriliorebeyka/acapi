package com.rebeyka.acapi.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.check.AbstractCheck;
import com.rebeyka.acapi.check.Checker;

public class Trigger {

	private static final Logger LOG = LogManager.getLogger();

	private String triggerOnActionableId;

	private AbstractCheck<?,Actionable,Actionable> condition;

	private Play playToTrigger;

	public Trigger(AbstractCheck<?,Actionable,Actionable> condition, Play playToTrigger, String triggerOnActionableId) {
		this.condition = condition;
		this.playToTrigger = playToTrigger;
		this.triggerOnActionableId = triggerOnActionableId;
	}

	public Trigger(Play playToTrigger, String triggerOnActionableId) {
		this(Checker.whenActionable().always(), playToTrigger, triggerOnActionableId);
	}

	public Trigger(Play playToTrigger) {
		this(playToTrigger, "ALL");
	}

	public boolean test(Actionable triggeringActionable) {
		if (this.equals(triggeringActionable.getParent().getTriggeredBy())) {
			return false;
		}
		LOG.info("Testing trigger %s against %s".formatted(triggerOnActionableId,
				triggeringActionable.getActionableId()));
		boolean matchingId = triggerOnActionableId.equals("ALL")
				|| triggerOnActionableId.equals(triggeringActionable.getActionableId());
		if (matchingId && condition.check(triggeringActionable)) {
			LOG.debug("Test passed, triggering play {}", playToTrigger.getName());
			return true;
		} else {
			return false;
		}
	}

	public Play getTriggeredPlay(Play base) {
		Play play = new Play.Builder(playToTrigger).targets(base.getTargets())
				.game(base.getGame()).triggeredBy(this)
				.origin(base.getOrigin()).build();
		return play;
	}

}
