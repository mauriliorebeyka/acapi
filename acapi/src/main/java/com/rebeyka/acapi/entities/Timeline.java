package com.rebeyka.acapi.entities;

import java.util.LinkedList;
import java.util.List;

public class Timeline {

	private List<Actionable> actionables;

	private int currentPosition;

	public Timeline() {
		actionables = new LinkedList<>();
		currentPosition = 0;
	}

	public void queue(Script script) {
		actionables.addAll(actionables.size(), script.getActionables());
	}

	public void queueNext(Script script) {
		actionables.addAll(currentPosition, script.getActionables());
	}

	public void executeNext() {
		if (currentPosition < actionables.size()) {
			actionables.get(currentPosition).execute();
			currentPosition++;
		}
	}

	public void rollbackLast() {
		if (currentPosition > 0) {
			actionables.remove(currentPosition);
			currentPosition--;
			actionables.get(currentPosition).rollback();
		}
	}

}
