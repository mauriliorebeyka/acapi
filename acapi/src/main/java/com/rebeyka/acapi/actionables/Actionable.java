package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.check.Checkable;
import com.rebeyka.acapi.entities.GameEntity;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.exceptions.ActionableCopyException;

public abstract class Actionable extends GameEntity implements Cloneable {

	private Play parent;

	public Actionable(String id) {
		super(id);
	}

	public abstract void execute();

	public abstract void rollback();

	public abstract String getMessage();

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
			copy.setGame(copy.parent.getGame());
			return copy;
		} catch (CloneNotSupportedException e) {
			throw new ActionableCopyException(e);
		}
	}
	
}
