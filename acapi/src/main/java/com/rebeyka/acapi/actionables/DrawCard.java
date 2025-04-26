package com.rebeyka.acapi.actionables;

import com.rebeyka.acapi.entities.Card;
import com.rebeyka.acapi.entities.Deck;

public class DrawCard extends Actionable {

	private Deck originDeck;

	private Deck targetDeck;

	private Card target;
	
	public DrawCard(String actionableId, Deck originDeck, Deck targetDeck) {
		super(actionableId);
		this.originDeck = originDeck;
		this.targetDeck = targetDeck;
	}

	@Override
	public void execute() {
		Card card = originDeck.draw();
		target = card;
		targetDeck.add(card);
	}

	@Override
	public void rollback() {
		originDeck.add(targetDeck.getCards().get(targetDeck.getCards().size() - 1));
	}

	@Override
	public String getMessage() {
		return "moving %s from %s to %s".formatted(target, originDeck, targetDeck);
	}
}
