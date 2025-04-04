package com.rebeyka.acapi.actionables.gameflow;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Playable;

public class EndTurnActionable extends Actionable {

	public EndTurnActionable() {
		super("EndTurn");
	}

	@Override
	public void execute() {
		getParent().getOrigin().getGame().getGameFlow().nextTurn();
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMessage() {
		return "%s ended their turn. Currently on round %s".formatted(getParent().getOrigin(),getParent().getOrigin().getGame().getGameFlow().getRound());
	}

}
