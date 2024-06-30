package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.FreeCost;

public class FreeCostActionable extends CostActionable {

	public FreeCostActionable(FreeCost cost) {
		super("Free Cost", cost);
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
