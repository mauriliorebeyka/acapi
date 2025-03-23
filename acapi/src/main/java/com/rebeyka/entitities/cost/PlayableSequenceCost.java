package com.rebeyka.entitities.cost;

import java.util.List;

import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.SimpleIntegerAttribute;

public class PlayableSequenceCost extends Cost {

	String attributeName;

	private List<Playable> playables;

	public PlayableSequenceCost(String attributeName, List<Playable> playables) {
		this.attributeName = attributeName;
		this.playables = playables;
	}

	@Override
	public boolean isPaid() {
		if (!playables.stream().map(p -> p.getAttribute(attributeName))
				.allMatch(a -> a instanceof SimpleIntegerAttribute)) {
			return false;
		}
		List<Integer> sorted = playables.stream().map(p -> (SimpleIntegerAttribute) p.getAttribute(attributeName))
				.map(SimpleIntegerAttribute::getValue).sorted().toList();
		return sorted.stream().skip(1).allMatch(i -> i - sorted.get(sorted.indexOf(i) - 1) == 1);
	}

}
