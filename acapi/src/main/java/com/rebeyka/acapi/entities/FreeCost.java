package com.rebeyka.acapi.entities;

import com.rebeyka.acapi.actionables.CostActionable;
import com.rebeyka.acapi.actionables.FreeCostActionable;

public class FreeCost extends Cost {

	public FreeCost() {
		setCostActionable(new FreeCostActionable(this));
	}

	@Override
	public boolean isPaid() {
		return true;
	}
}
