package com.rebeyka.acapi.entities;

public abstract class CostActionable extends ChoiceActionable {

	private Cost cost;

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
