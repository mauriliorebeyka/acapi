package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Playable;

public abstract class Actionable {

	private String actionableId;

	private Play parent;

	private Playable playable;

	public Actionable(String actionableId, Playable playable) {
		this.actionableId = actionableId;
		this.playable = playable;
	}

	public abstract void execute();

	public abstract void rollback();

	public abstract String getMessage();

	public String getActionableId() {
		return actionableId;
	}

	public Play getParent() {
		return parent;
	}

	public void setParent(Play parent) {
		this.parent = parent;
	}

	public Playable getPlayable() {
		return playable;
	}

	public void setPlayable(Playable target) {
		this.playable = target;
	}
}
