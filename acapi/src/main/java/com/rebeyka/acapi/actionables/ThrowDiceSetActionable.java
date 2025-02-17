package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.random.DiceSet;

public class ThrowDiceSetActionable<T> extends Actionable {

	private DiceSet<T> dice;
	
	public ThrowDiceSetActionable(String actionableId, DiceSet<T> dice, Playable playable) {
		super(actionableId, playable);
		this.dice = dice;
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
		return "Rolling %s dice. Values %s".formatted(getDice().getCount(),getDice());
	}

	public DiceSet<T> getDice() {
		return dice;
	}

}
