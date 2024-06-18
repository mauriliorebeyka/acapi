package com.rebeyka.acapi.entities;

public abstract class Actionable {

	private Play parent;

	public Actionable(Play parent) {
		this.parent = parent;
	}

	public abstract void execute();

	public abstract void rollback();

	public Play getParent() {
		return parent;
	}

}
