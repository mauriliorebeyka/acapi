package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Cost;

public abstract class CostActionable extends ChoiceActionable {

	private Cost cost;

	public CostActionable(String actionableId, Cost cost) {
		super(actionableId);
		this.cost = cost;
	}

	@Override
	public boolean isSet() {
		return super.isSet() && cost.isPaid();
	}

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

}
