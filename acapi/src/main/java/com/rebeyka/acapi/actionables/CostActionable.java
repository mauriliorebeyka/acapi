package com.rebeyka.acapi.actionables;

import java.util.List;

import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Playable;

public abstract class CostActionable extends ConditionalActionable {

	private Cost cost;

	public CostActionable(String actionableId, Playable origin, Cost cost) {
		super(actionableId, origin);
		this.cost = cost;
	}

	@Override
	public boolean isSet() {
		return cost.isPaid(getSelectedChoices());
	}

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

	@Override
	public String getMessage() {
		return "Paying cost %s with playables %s".formatted(cost,getSelectedChoices());
	}

	@Override
	public void execute() {
		getSelectedChoices().forEach(this::executeSingle);
	}
	
	@Override
	public void rollback() {
		getSelectedChoices().forEach(this::rollbackSingle);
	}
	
	public List<Playable> getSelectedChoices() {
		return getOrigin().getGame().getSelectedChoices();
	}
	
	public abstract void executeSingle(Playable playable);
	
	public abstract void rollbackSingle(Playable playable);
}
