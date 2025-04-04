package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Card;
import com.rebeyka.acapi.entities.Game;

public class MoveCardActionable extends Actionable {

	private String originDeck;
	
	private String targetDeck;
	
	public MoveCardActionable(String actionableId, String originDeck, String targetDeck) {
		super(actionableId);
		this.originDeck = originDeck;
		this.targetDeck = targetDeck;
	}

	@Override
	public void execute() {
		Game game = getParent().getOrigin().getGame();
		game.findDeck(originDeck).getCards().removeAll(getParent().getTargets());
		game.findDeck(targetDeck).getCards().addAll(getParent().getTargets().stream().map(c -> (Card)c).toList());
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMessage() {
		return "moving %s from %s to %s".formatted(getParent().getTargets(),originDeck,targetDeck);
	}

	
	
}
