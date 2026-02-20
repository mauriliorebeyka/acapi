package com.rebeyka.acapi.actionables;

import java.util.function.Supplier;

import com.rebeyka.acapi.entities.PlayArea;

public class MoveCardActionable extends Actionable {

	private PlayArea originPlayArea;
	
	private PlayArea targetPlayArea;
	
	public MoveCardActionable(String actionableId, PlayArea originPlayArea, PlayArea targetPlayArea) {
		super(actionableId);
		this.originPlayArea = originPlayArea;
		this.targetPlayArea = targetPlayArea;
	}

	@Override
	public void execute() {
		originPlayArea.getAllPlayables().removeAll(getParent().getTargets());
		targetPlayArea.getAllPlayables().addAll(getParent().getTargets().stream().map(c -> c).toList());
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMessage() {
		return "moving %s from %s to %s".formatted(getParent().getTargets(),originPlayArea,targetPlayArea);
	}

	@Override
	public Supplier<Actionable> supply() {
		return () -> new MoveCardActionable(getActionableId(), originPlayArea, targetPlayArea);
	}
}
