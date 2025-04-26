package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.check.AbstractCheck;
import com.rebeyka.acapi.entities.Play;

public abstract class Actionable {

	private String actionableId;

	private Play parent;

	public Actionable(String actionableId) {
		this.actionableId = actionableId;
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

	public boolean check(AbstractCheck<?,Actionable,?> condition) {
		return condition.check(this);
	}
}
