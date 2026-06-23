package com.rebeyka.acapi.actionables.gameflow;

import com.rebeyka.acapi.actionables.Actionable;

public class EndRoundActionable extends Actionable{

	public EndRoundActionable() {
		super("End round");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		getParent().getGame().getGameFlow().nextRound();
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMessage() {
		return "Ending round";
	}


}
