package com.rebeyka.acapi.actionables;

import java.util.List;
import java.util.function.Supplier;

import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Playable;

public class SimpleCostActionable extends CostActionable {

	private Supplier<Actionable> actionableSupplier;
	
	public SimpleCostActionable(String actionableId, Cost cost, Supplier<Actionable> actionable) {
		super(actionableId, cost);
		this.actionableSupplier = actionable;
	}

	@Override
	public void executeSingle(Playable playable) {
		Actionable actionable = actionableSupplier.get();
		Play temp = new Play(getParent());
		temp.setCost(Cost.FREE);
		temp.setTargets(List.of(playable));
		actionable.setParent(temp);
		//TODO Find a way to put this into the timeline for execution, so we could maintain the
		//standard logging messages
//		temp.getGame().declarePlay(null, temp, List.of(playable));
		actionable.execute();
	}

	@Override
	public void rollbackSingle(Playable playable) {
		Actionable actionable = actionableSupplier.get();
		Play temp = new Play(getParent());
		temp.setTargets(List.of(playable));
		actionable.setParent(temp);
		actionable.rollback();
	}

	
}
