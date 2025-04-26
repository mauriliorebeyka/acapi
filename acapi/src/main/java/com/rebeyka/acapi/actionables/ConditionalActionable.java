package com.rebeyka.acapi.actionables;

public abstract class ConditionalActionable extends Actionable {

	public ConditionalActionable(String actionableId) {
		super(actionableId);
	}

	public abstract boolean isSet();

}
