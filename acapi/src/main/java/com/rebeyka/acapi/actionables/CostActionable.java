package com.rebeyka.acapi.actionables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.gameflow.Play;

public abstract class CostActionable extends ConditionalActionable {

	protected static Logger LOG = LogManager.getLogger();

	private Cost cost;

	private List<Playable> selectedChoices;

	private List<Play> costPlays;

	public CostActionable(String actionableId, Cost cost) {
		super(actionableId);
		this.cost = cost;
		this.costPlays = new ArrayList<>();
	}

	@Override
	public boolean isSet() {
		return cost.isPaid(getParent().getGame().getSelectedChoices());
	}

	@Override
	public String getMessage() {
		return "Paying cost %s with playables %s".formatted(cost, selectedChoices);
	}

	public List<Play> getCostPlays() {
		return costPlays;
	}

	@Override
	public void execute() {
		selectedChoices = getParent().getGame().getSelectedChoices();
		Play.Builder template = new Play.Builder().name("cost of " + getParent().getName()).cost(null)
				.origin(getParent().getOrigin()).game(getParent().getGame());
		costPlays = selectedChoices.reversed().stream()//.filter(p -> generatePlay(p) != null)
				.map(p -> template.actionable(getActionable(p)).target(p).build()).toList();
		getParent().getGame().setSelectedChoices(Collections.emptyList());
	}

	@Override
	public void rollback() {
//		costPlays.reversed().forEach(p -> p);
	}

	public abstract Supplier<Actionable> getActionable(Playable playable);

}
