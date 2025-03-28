package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.FreeCost;
import com.rebeyka.acapi.entities.Playable;

public class FreeCostActionable extends CostActionable {

	public FreeCostActionable(Playable origin, FreeCost cost) {
		super("Free Cost", origin, cost);
	}

	@Override
	public String getMessage() {
		return "Free cost";
	}

	@Override
	public void executeSingle(Playable playable) {
		//NOTHING
	}
	
	@Override
	public void rollbackSingle(Playable playable) {
		//NOTHING
	}
}
