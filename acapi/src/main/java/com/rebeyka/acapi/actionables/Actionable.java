package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Playable;

public abstract class Actionable {

	private String actionableId;

	private Play parent;

	private Playable origin;

	public Actionable(String actionableId, Playable origin) {
		this.actionableId = actionableId;
		this.origin = origin;
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

	public Playable getOrigin() {
		return origin;
	}

	public void setOrigin(Playable origin) {
		this.origin = origin;
	}
}
