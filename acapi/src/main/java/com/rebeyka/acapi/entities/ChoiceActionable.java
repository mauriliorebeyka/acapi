package com.rebeyka.acapi.entities;

import java.util.List;

public abstract class ChoiceActionable extends Actionable {

	private List<Actionable> choices;

	private List<Actionable> chosen;

	private boolean set;

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

	public void set(boolean set) {
		this.set = set;
	}

	public boolean isSet() {
		return set;
	}

}
