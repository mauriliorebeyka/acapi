package com.rebeyka.acapi.actionables;

import java.util.List;

import com.rebeyka.acapi.entities.Playable;

public abstract class ChoiceActionable extends ConditionalActionable {

	private List<Actionable> choices;

	private List<Actionable> chosen;

	public ChoiceActionable(String actionableId, Playable origin) {
		super(actionableId, origin);
	}

	public List<Actionable> getChoices() {
		return choices;
	}

	public void setChoices(List<Actionable> choices) {
		this.choices = choices;
	}

	public List<Actionable> getChosen() {
		return chosen;
	}

	public void setChosen(List<Actionable> chosen) {
		this.chosen = chosen;
	}

}
