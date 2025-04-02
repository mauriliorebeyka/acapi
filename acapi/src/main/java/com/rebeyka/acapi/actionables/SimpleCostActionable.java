package com.rebeyka.acapi.actionables;

import java.util.List;

import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Playable;

public class SimpleCostActionable extends CostActionable {

	private Actionable actionable;
	
	public SimpleCostActionable(String actionableId, Playable origin, Cost cost, Actionable actionable) {
		super(actionableId, origin, cost);
		this.actionable = actionable;
	}

	@Override
	public void executeSingle(Playable playable) {
		actionable.setTargets(List.of(playable));
		actionable.execute();
	}

	@Override
	public void rollbackSingle(Playable playable) {
		actionable.setTargets(List.of(playable));
		actionable.rollback();
	}

	
}
