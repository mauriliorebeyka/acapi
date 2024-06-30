package com.rebeyka.acapi.actionables;

import java.util.function.Supplier;

import com.rebeyka.acapi.entities.Card;
import com.rebeyka.acapi.entities.Deck;

public class MoveCard extends Actionable {

	private Supplier<Deck> originDeck;

	private Supplier<Deck> targetDeck;

	public MoveCard(String actionableId, Supplier<Deck> origin, Supplier<Deck> target) {
		super(actionableId);
		this.originDeck = origin;
		this.targetDeck = target;
	}

	@Override
	public void execute() {
		Card card = originDeck.get().draw();
		System.out.println("moving %s from %s to %s".formatted(card.getAttribute("value").getValue(), originDeck.get(),
				targetDeck.get()));
		setPlayable(card);
		targetDeck.get().add(card);
	}

	@Override
	public void rollback() {
		originDeck.get().add(targetDeck.get().getCards().get(targetDeck.get().getCards().size() - 1));
	}

}
