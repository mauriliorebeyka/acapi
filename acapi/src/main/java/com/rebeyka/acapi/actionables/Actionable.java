package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.check.Checkable;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.exceptions.ActionableCopyException;

public abstract class Actionable implements Cloneable {

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

	public boolean check(Checkable<Actionable> condition) {
		return condition.check(this);
	}
	
	protected Actionable doClone() throws CloneNotSupportedException {
		return (Actionable)this.clone();
	}
	
	public Actionable copy(Play newParent) {
		try {
			Actionable copy = (Actionable) doClone();
			copy.parent = newParent;
			return copy;
		} catch (CloneNotSupportedException e) {
			throw new ActionableCopyException(e);
		}
	}
	
}
