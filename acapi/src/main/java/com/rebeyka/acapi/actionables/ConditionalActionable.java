package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Playable;

public abstract class ConditionalActionable extends Actionable {

	public ConditionalActionable(String actionableId, Playable origin) {
		super(actionableId, origin);
	}

	public abstract boolean isSet();

}
