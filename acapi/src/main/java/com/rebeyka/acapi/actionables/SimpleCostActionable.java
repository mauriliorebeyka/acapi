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
		Play temp = new Play.Builder().name("cost of " + getParent().getName()).cost(null)
				.actionable(actionableSupplier).origin(getParent().getOrigin()).game(getParent().getGame()).build();
		temp.getGame().declarePlay(temp, List.of(playable), true);
	}

	@Override
	public void rollbackSingle(Playable playable) {
		Actionable actionable = actionableSupplier.get();
		Play temp = new Play.Builder(getParent()).target(playable).build();
		actionable.setParent(temp);
		actionable.rollback();
	}
	
}
