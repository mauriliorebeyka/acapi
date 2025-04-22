package com.rebeyka.acapi.entities.cost;

import java.util.List;
import java.util.function.Predicate;

import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Playable;

public abstract class CardCountCost extends Cost {

	private Predicate<Playable> predicate;

	private int count;

	public CardCountCost(int count, Predicate<Playable> predicate) {
		this.count = count;
		this.predicate = predicate;
	}

	public CardCountCost(int count) {
		this(count, _ -> true);
	}

	public CardCountCost() {
		this(1, _ -> true);
	}

	@Override
	public boolean isPaid(List<Playable> cards) {
		return cards.stream().allMatch(predicate) && cards.size() >= count;
	}

}
