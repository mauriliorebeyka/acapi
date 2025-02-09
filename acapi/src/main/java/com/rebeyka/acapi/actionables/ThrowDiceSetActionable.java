package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.random.DiceSet;

public class ThrowDiceSetActionable<T> extends Actionable {

	private DiceSet<T> dice;
	
	public ThrowDiceSetActionable(String actionableId, DiceSet<T> dice, Playable playable) {
		super(actionableId);
		this.dice = dice;
		this.setPlayable(playable);
	}

	@Override
	public void execute() {
		this.getDice().rollAll();
		getPlayable().setAttribute("DICE_ROLL", new Attribute<DiceSet<T>>(dice));
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
