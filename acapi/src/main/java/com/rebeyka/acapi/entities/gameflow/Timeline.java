package com.rebeyka.acapi.entities.gameflow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.ConditionalActionable;
import com.rebeyka.acapi.actionables.CostActionable;
import com.rebeyka.acapi.entities.Game;

public class Timeline {

	private static final Logger LOG = LogManager.getLogger();

	private List<Actionable> actionables;

	private List<LogEntry> logMessages;

	private int currentPosition;

	private Game game;

	public Timeline(Game game) {
		this.game = game;
		actionables = new LinkedList<>();
		logMessages = new ArrayList<>();
		currentPosition = 0;
	}

	public Actionable getCurrent() {
		if (currentPosition == actionables.size()) {
			return null;
		}
		return actionables.get(currentPosition);
	}

	public void queue(Play newPlay) {
		queue(newPlay, false);
	}

	public void queue(Play newPlay, boolean skipCurrentQueue) {
		LOG.info("Declaring play {} from {} against {}", newPlay.getName(), newPlay.getOrigin(), newPlay.getTargets());
		int position = skipCurrentQueue ? currentPosition : actionables.size();
		if (newPlay.getCost() != null) {
			CostActionable costActionable = newPlay.getCost().getCostActionable();
			costActionable.setParent(newPlay);
			actionables.add(position++,costActionable);
		}
		actionables.addAll(position,newPlay.getActionables());
	}
	
	public List<LogEntry> getLogMessages() {
		return logMessages;
	}

	public boolean hasNext() {
		return currentPosition < actionables.size();
	}

	public boolean isNextConditional() {
		return hasNext() && actionables.get(currentPosition) instanceof ConditionalActionable;
	}

	public boolean executeNext() {
		if (hasNext()) {
			Actionable actionable = actionables.get(currentPosition);
			LOG.debug("Executing {}. Still {} actionables in the list ", actionable.getActionableId(),
					actionables.size() - currentPosition);
			if (actionable instanceof ConditionalActionable conditionalActionable && !conditionalActionable.isSet()) {
				if (conditionalActionable instanceof CostActionable) {
					LOG.debug("Cost {} not paid", conditionalActionable);
					cancelCurrentPlay();
				}
				return false;
			} else {
				execute();
				if (actionable instanceof CostActionable costActionable) {
					costActionable.getCostPlays().forEach(play -> queue(play, true));
				}
				return true;
			}
		}
		LOG.debug("No actionables to execute");
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
		String message = actionable.getMessage();
		LOG.info(message);
		logMessages.add(new LogEntry(actionable.getParent(), message));
		currentPosition++;
		if (currentPosition < actionables.size()) {
			game.getBeforeTriggerActionables(actionables.get(currentPosition)).forEach(p -> queue(p,true));
		}
		game.getAfterTriggerActionables(actionable).forEach(p -> queue(p,true));
	}

	public void cancelCurrentPlay() {
		if (currentPosition == actionables.size()) {
			return;
		}
		Play play = actionables.get(currentPosition).getParent();
		LOG.debug("Cancelling play {}", play);
		while (currentPosition > 0 && actionables.get(currentPosition - 1).getParent().equals(play)) {
			rollbackLast();
		}
		while (currentPosition < actionables.size() && actionables.get(currentPosition).getParent().equals(play)) {
			actionables.remove(currentPosition);
		}
	}

	public void clearNonExecutedActionables() {
		while (currentPosition < actionables.size() - 1) {
			actionables.remove(actionables.size() - 1);
		}
	}

	public List<Actionable> getQueuedActionables() {
		return actionables.subList(currentPosition, actionables.size());
	}
	
	public List<Actionable> getExecutedActionables() {
		return new ArrayList<>(actionables.subList(0, currentPosition));
	}

}
