package com.rebeyka.acapi.actionables;

import java.util.function.Supplier;

import com.rebeyka.acapi.entities.Card;
import com.rebeyka.acapi.entities.Deck;

public class MoveCardActionable extends Actionable {

	private Deck originDeck;
	
	private Deck targetDeck;
	
	public MoveCardActionable(String actionableId, Deck originDeck, Deck targetDeck) {
		super(actionableId);
		this.originDeck = originDeck;
		this.targetDeck = targetDeck;
	}

	@Override
	public void execute() {
		originDeck.getCards().removeAll(getParent().getTargets());
		targetDeck.getCards().addAll(getParent().getTargets().stream().map(c -> (Card)c).toList());
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMessage() {
		return "moving %s from %s to %s".formatted(getParent().getTargets(),originDeck,targetDeck);
	}

	@Override
	public Supplier<Actionable> supply() {
		return () -> new MoveCardActionable(getActionableId(), originDeck, targetDeck);
	}
}
