package com.rebeyka.acapi.entities.gameflow;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.ChoiceActionable;
import com.rebeyka.acapi.actionables.CostActionable;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Play;

public class Timeline {

	private static final Logger LOG = LogManager.getLogger();

	private List<Actionable> actionables;

	private int currentPosition;

	private Game game;

	public Timeline(Game game) {
		this.game = game;
		actionables = new LinkedList<>();
		currentPosition = 0;
	}

	public Actionable getCurrent() {
		if (currentPosition == 0 || currentPosition == actionables.size()) {
			return null;
		}
		return actionables.get(currentPosition);
	}

	public void queue(Play play) {
		LOG.info("Declaring play {} from {}", play.getId(), play.getOrigin());
		actionables.add(play.getCost().generateActionable());
		actionables.addAll(actionables.size(), play.getActionables());
	}

	public boolean executeNext() {
		LOG.debug("Executing next actionable. Still {} in the list ",actionables.size() - currentPosition);
		if (currentPosition < actionables.size()) {
			Actionable actionable = actionables.get(currentPosition);
			if (actionable instanceof ChoiceActionable choiceActionable && !choiceActionable.isSet()) {
				if (choiceActionable instanceof CostActionable) {
					cancelCurrentPlay();
				}
				return false;
			} else {
				execute();
				return true;
			}
		}
		return false;
	}

	public void rollbackLast() {
		if (currentPosition > 0) {
			currentPosition--;
			actionables.get(currentPosition).rollback();
		}
	}

	private void execute() {
		Actionable actionable = actionables.get(currentPosition);
		actionable.execute();
		LOG.info(actionable.getMessage());
		currentPosition++;
		if (currentPosition < actionables.size()) {
			actionables.addAll(currentPosition, game.getFutureTriggerActionables(actionables.get(currentPosition)));
		}
		actionables.addAll(currentPosition, game.getPastTriggerActionablesActionables(actionable));
	}

	public void cancelCurrentPlay() {
		if (currentPosition == actionables.size()) {
			return;
		}
		Play play = actionables.get(currentPosition).getParent();
		while (currentPosition > 0 && actionables.get(currentPosition - 1).getParent().equals(play)) {
			rollbackLast();
		}
		while (currentPosition < actionables.size() && actionables.get(currentPosition).getParent().equals(play)) {
			actionables.remove(currentPosition);
		}
	}
}
