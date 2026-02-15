package com.rebeyka.acapi.entities.gameflow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.rebeyka.acapi.entities.Player;

public class RankingByAttribute extends Ranking {

	private List<String> attributeName;

	private Comparator<Player> comparator;

	public RankingByAttribute(String attributeName) {
		this(List.of(attributeName));
	}

	public RankingByAttribute(List<String> attributeName) {
		super();
		this.attributeName = attributeName;
		comparator = Comparator.nullsLast(null);
		for (String attribute : attributeName) {
			comparator = comparator.thenComparing(Comparator.comparing(p -> p.getRawAttribute(attribute),
					Comparator.nullsFirst(Comparator.naturalOrder())));
		}
	}

	@Override
	public List<RankingPosition> getUpdatedRank(List<Player> players) {
		List<Player> sorted = new ArrayList<>(players);
		sorted.sort(comparator.reversed());

		List<RankingPosition> rank = new ArrayList<>();
		Player current = null;
		RankingPosition currentPosition = null;
		for (int i = 0; i < sorted.size(); i++) {
			Player next = sorted.get(i);
			if (comparator.compare(current, next) != 0) {
				List<String> finalScore = attributeName.stream()
						.map(a -> (next.getRawAttribute(a) != null ? next.getRawAttribute(a).getValue().toString() : "no")
								+ " " + a)
						.toList();
				currentPosition = new RankingPosition(i + 1, next.getId(), finalScore);
				rank.add(currentPosition);
			} else {
				currentPosition.getPlayers().add(next.getId());
			}
			current = next;
		}
		return rank;
	}
}
