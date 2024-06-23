package com.rebeyka.acapi.entities;

public class FreeCostActionable extends CostActionable {

	public FreeCostActionable(FreeCost cost) {
		super(cost);
		set(true);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub

	}

}
