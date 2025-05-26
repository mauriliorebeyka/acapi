package com.rebeyka.acapi.actionables;

import java.util.function.Supplier;

import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Playable;

public class SimpleCostActionable extends CostActionable {

	private Supplier<Actionable> actionableSupplier;

	public SimpleCostActionable(String actionableId, Cost cost, Supplier<Actionable> actionable) {
		super(actionableId, cost);
		this.actionableSupplier = actionable;
	}

	@Override
	public Supplier<Actionable> getActionable(Playable playable) {
		return actionableSupplier;
	}
	
}
