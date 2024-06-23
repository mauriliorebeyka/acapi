package com.rebeyka.acapi.entities;

public class FreeCost extends Cost {

	private CostActionable actionable;

	public FreeCost() {
		actionable = new FreeCostActionable(this);
	}

	@Override
	public CostActionable generateActionable() {
		return actionable;
	}

	@Override
	public boolean isPaid() {
		return true;
	}
}
