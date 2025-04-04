package com.rebeyka.acapi.entities;

import java.util.List;
import java.util.function.Supplier;

import com.rebeyka.acapi.actionables.CostActionable;

public abstract class Cost {

	private Supplier<CostActionable> costActionable;
	
	public void setCostActionable(Supplier<CostActionable> costActionable) {
		this.costActionable = costActionable;
	}
	
	public CostActionable getCostActionable() {
		return costActionable.get();
	}
	
	public abstract boolean isPaid(List<Playable> playables);

	public static Cost FREE = null;
	
}
