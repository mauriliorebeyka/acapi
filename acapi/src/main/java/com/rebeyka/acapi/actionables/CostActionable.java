package com.rebeyka.acapi.actionables;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Playable;

public abstract class CostActionable extends ConditionalActionable {

	protected static Logger LOG = LogManager.getLogger();
	
	private Cost cost;
	
	private List<Playable> selectedChoices;

	public CostActionable(String actionableId, Cost cost) {
		super(actionableId);
		this.cost = cost;
	}

	@Override
	public boolean isSet() {
		return cost.isPaid(getParent().getGame().getSelectedChoices());
	}

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

	@Override
	public String getMessage() {
		return "Paying cost %s with playables %s".formatted(cost,selectedChoices);
	}

	@Override
	public void execute() {
		selectedChoices = getParent().getGame().getSelectedChoices();
		selectedChoices.reversed().forEach(this::executeSingle);
		getParent().getGame().setSelectedChoices(Collections.emptyList());
	}
	
	@Override
	public void rollback() {
		selectedChoices.reversed().forEach(this::rollbackSingle);
	}
	
	public abstract void executeSingle(Playable playable);
	
	public abstract void rollbackSingle(Playable playable);
}
