package com.rebeyka.acapi.actionables;

import java.util.List;

public abstract class ChoiceActionable extends ConditionalActionable {

	private List<Actionable> choices;

	private List<Actionable> chosen;

	public ChoiceActionable(String actionableId) {
		super(actionableId);
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
