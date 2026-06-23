package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.FreeCost;
import com.rebeyka.acapi.entities.Playable;

public class FreeCostActionable extends CostActionable {

	public FreeCostActionable(FreeCost cost) {
		super("Free Cost", cost);
	}

	@Override
	public String getMessage() {
		return "Free cost";
	}

	@Override
	public Actionable getActionable(Playable playable) {
		return new EmptyActionable();
	}
	
	
}
