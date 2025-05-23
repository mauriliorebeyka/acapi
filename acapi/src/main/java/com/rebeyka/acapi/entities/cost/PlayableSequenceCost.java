package com.rebeyka.acapi.entities.cost;

import java.util.List;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Types;

public class PlayableSequenceCost extends Cost {

	String attributeName;

	public PlayableSequenceCost(String attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	public boolean isPaid(List<Playable> playables) {
		if (!playables.stream().map(p -> p.getAttribute(attributeName, Types.integer()))
				.allMatch(this::validAttribute)) {
			return false;
		}
		List<Integer> sorted = playables.stream().map(p -> p.getAttribute(attributeName, Types.integer()))
				.map(Attribute::getValue).sorted().toList();
		if (sorted.isEmpty()) {
			return false;
		}
		return sorted.stream().skip(1).allMatch(i -> i - sorted.get(sorted.indexOf(i) - 1) == 1);
	}

	private boolean validAttribute(Attribute<Integer> attribute) {
		return attribute.getType().equals(Types.integer()) && attribute.getValue() != null;
	}
}
