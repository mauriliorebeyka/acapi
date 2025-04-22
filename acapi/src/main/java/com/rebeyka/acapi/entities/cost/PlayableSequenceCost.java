package com.rebeyka.acapi.entities.cost;

import java.util.List;

import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.SimpleIntegerAttribute;

public class PlayableSequenceCost extends Cost {

	String attributeName;

	public PlayableSequenceCost(String attributeName) {
		this.attributeName = attributeName;
	}
	
	@Override
	public boolean isPaid(List<Playable> playables) {
		if (!playables.stream().map(p -> p.getAttribute(attributeName))
				.allMatch(a -> a instanceof SimpleIntegerAttribute)) {
			return false;
		}
		List<Integer> sorted = playables.stream().map(p -> (SimpleIntegerAttribute) p.getAttribute(attributeName))
				.map(SimpleIntegerAttribute::getValue).sorted().toList();
		if (sorted.isEmpty()) {
			return false;
		}
		return sorted.stream().skip(1).allMatch(i -> i - sorted.get(sorted.indexOf(i) - 1) == 1);
	}

}
