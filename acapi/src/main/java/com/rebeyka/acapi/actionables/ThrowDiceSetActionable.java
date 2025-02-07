package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.random.DiceSet;

public class ThrowDiceSetActionable<T> extends Actionable {

	private DiceSet<T> dice;
	
	public ThrowDiceSetActionable(String actionableId, DiceSet<T> dice) {
		super(actionableId);
		this.dice = dice;
	}

	@Override
	public void execute() {
		this.getDice().rollAll();
	}

	@Override
	public void rollback() {
		//TODO
	}

	@Override
	public String getMessage() {
		return "Rolling %s dice".formatted(getDice().getCount());
	}

	public DiceSet<T> getDice() {
		return dice;
	}

}
