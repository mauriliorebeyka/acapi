package com.rebeyka.acapi.actionables;

import java.util.function.Supplier;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Types;
import com.rebeyka.acapi.random.DiceSet;

public class ThrowDiceSetActionable<T> extends Actionable {

	private DiceSet<T> dice;
	
	public ThrowDiceSetActionable(String actionableId, DiceSet<T> dice) {
		super(actionableId);
		this.dice = dice;
	}

	@Override
	public void execute() {
		this.getDice().rerollAll();
		Attribute<DiceSet<T>> attr = getParent().getOrigin().getAttribute("DICE_ROLL", Types.diceSetOf(dice));
		attr.setValue(dice);
	}

	@Override
	public void rollback() {
		//TODO
	}

	@Override
	public String getMessage() {
		return "Rolling %s dice. Values %s".formatted(getDice().getCount(),getDice());
	}

	@Override
	public Supplier<Actionable> supply() {
		return () -> new ThrowDiceSetActionable<T>(getActionableId(), dice);
	}
	
	public DiceSet<T> getDice() {
		return dice;
	}

}
