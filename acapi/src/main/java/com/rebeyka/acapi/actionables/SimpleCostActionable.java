package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Playable;

public class SimpleCostActionable extends CostActionable {

	private Actionable actionableSupplier;

	public SimpleCostActionable(String actionableId, Cost cost, Actionable actionable) {
		super(actionableId, cost);
		this.actionableSupplier = actionable;
	}

	@Override
	public Actionable getActionable(Playable playable) {
		return actionableSupplier.copy(getParent());
	}

}
