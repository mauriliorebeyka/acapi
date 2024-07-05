package com.rebeyka.acapi.actionables.gameflow;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.gameflow.PlayerOrder;

public class EndTurnActionable extends Actionable {

	private PlayerOrder playerOrder;

	public EndTurnActionable(PlayerOrder playerOrder) {
		super("EndTurn");
		this.playerOrder = playerOrder;
	}

	@Override
	public void execute() {
		setPlayable(playerOrder.getCurrentPlayer());
		playerOrder.endTurn();
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMessage() {
		return "%s ended their turn".formatted(getPlayable());
	}

}
