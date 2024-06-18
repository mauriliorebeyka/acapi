package com.rebeyka.acapi.entities;

public abstract class CostActionable extends ChoiceActionable {

	private Cost cost;

	public CostActionable(Play parent) {
		super(parent);
		this.cost = parent.getCost();
	}

	@Override
	public boolean isSet() {
		return super.isSet() && cost.isPaid();
	}
}
