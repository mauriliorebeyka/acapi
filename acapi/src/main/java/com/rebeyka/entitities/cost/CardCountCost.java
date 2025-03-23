package com.rebeyka.entitities.cost;

import java.util.List;
import java.util.function.Predicate;

import com.rebeyka.acapi.entities.Card;
import com.rebeyka.acapi.entities.Cost;

public abstract class CardCountCost extends Cost {

	private List<Card> cards;

	private Predicate<Card> predicate;

	private int count;

	public CardCountCost(int count, Predicate<Card> predicate) {
		this.count = count;
		this.predicate = predicate;
	}

	public CardCountCost(int count) {
		this(count, _ -> true);
	}

	public CardCountCost() {
		this(1, _ -> true);
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public List<Card> getCards() {
		return cards;
	}

	@Override
	public boolean isPaid() {
		return cards.stream().allMatch(predicate) && cards.size() >= count;
	}

}
