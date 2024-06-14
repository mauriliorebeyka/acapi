package com.rebeyka.acapi.entities;

public abstract class Actionable {

	private Script parent;

	public Actionable(Script parent) {
		this.parent = parent;
	}

	public abstract void execute();

	public abstract void rollback();

	public Script getParent() {
		return parent;
	}

}
