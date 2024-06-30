package com.rebeyka.acapi.entities;

import com.rebeyka.acapi.actionables.CostActionable;
import com.rebeyka.acapi.actionables.FreeCostActionable;

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
