package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Playable;

public abstract class ConditionalActionable extends Actionable {

	public ConditionalActionable(String actionableId) {
		super(actionableId);
	}

	public abstract boolean isSet();

}
