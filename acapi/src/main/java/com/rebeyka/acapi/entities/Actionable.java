package com.rebeyka.acapi.entities;

public abstract class Actionable {

	private Play parent;

	private Playable playable;

	public Actionable() {
	}

	public abstract void execute();

	public abstract void rollback();

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
