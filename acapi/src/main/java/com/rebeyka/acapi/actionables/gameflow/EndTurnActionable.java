package com.rebeyka.acapi.actionables.gameflow;

import java.util.function.Supplier;

import com.rebeyka.acapi.actionables.Actionable;

public class EndTurnActionable extends Actionable {

	private boolean pass;
	
	public EndTurnActionable() {
		this(true);
	}

	public EndTurnActionable(boolean pass) {
		super("EndTurn");
		this.pass = pass;
	}
	
	@Override
	public void execute() {
		getParent().getGame().getGameFlow().nextTurn(pass);
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMessage() {
		return "%s ended their turn. Currently on round %s".formatted(getParent().getOrigin(),getParent().getOrigin().getGame().getGameFlow().getRound());
	}

	@Override
	public Supplier<Actionable> supply() {
		return  () -> new EndTurnActionable(pass);
	}
}
