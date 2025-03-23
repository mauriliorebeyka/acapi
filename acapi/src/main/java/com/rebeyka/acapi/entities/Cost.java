package com.rebeyka.acapi.entities;

import com.rebeyka.acapi.actionables.CostActionable;

public abstract class Cost {

	private CostActionable costActionable;
	
	public void setCostActionable(CostActionable costActionable) {
		this.costActionable = costActionable;
	}
	
	public CostActionable getCostActionable() {
		return costActionable;
	}

	public abstract boolean isPaid();

	public static Cost FREE = new FreeCost();
}
