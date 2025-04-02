package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Card;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;

public class MoveCardActionable extends Actionable {

	private String originDeck;
	
	private String targetDeck;
	
	public MoveCardActionable(String actionableId, Playable origin, String originDeck, String targetDeck) {
		super(actionableId, origin);
		this.originDeck = originDeck;
		this.targetDeck = targetDeck;
	}

	@Override
	public void execute() {
		Game game = getOrigin().getGame();
		game.findDeck(originDeck).getCards().remove(getTargets().getFirst());
		game.findDeck(targetDeck).add((Card)getTargets().getFirst());
		System.out.println("moving %s from %s to %s".formatted(getTargets(),originDeck,targetDeck));
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "moving card";
	}

	
	
}
