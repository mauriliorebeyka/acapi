package com.rebeyka.acapi.entities;

import java.util.LinkedList;
import java.util.List;

public class Timeline {

	private List<Actionable> actionables;

	private int currentPosition;

	private Game game;

	public Timeline(Game game) {
		this.game = game;
		actionables = new LinkedList<>();
		currentPosition = 0;
	}

	public void queue(Play script) {
		if (script.getCost() != null) {
			actionables.add(script.getCost().generateActionable());
		}
		actionables.addAll(actionables.size(), script.getActionables());
	}

	public void queueNext(Play script) {
		actionables.addAll(currentPosition, script.getActionables());
	}

	public void executeNext() {
		if (currentPosition < actionables.size()) {
			Actionable actionable = actionables.get(currentPosition);
			switch (actionable) {
			case CostActionable costActionable:
				if (costActionable.isSet()) {
					execute();
				} else {
					cancelCurrentPlay();
				}
				break;
			case ChoiceActionable choiceActionable:
				if (choiceActionable.isSet()) {
					execute();
				}
				break;
			default:
				execute();
			}
		}
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
		currentPosition++;
		if (currentPosition < actionables.size()) {
			actionables.addAll(currentPosition, game.getTriggeringActionables(actionables.get(currentPosition)));
		}
		actionables.addAll(currentPosition, game.getTriggeredActionables(actionable));
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
