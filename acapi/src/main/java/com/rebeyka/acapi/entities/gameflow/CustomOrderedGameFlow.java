package com.rebeyka.acapi.entities.gameflow;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import com.rebeyka.acapi.entities.Player;

public class CustomOrderedGameFlow extends RoundRobinGameFlow {

	private int[] playerOrder;

	private int[] newOrder;

	public CustomOrderedGameFlow(GameFlowBuilder builder, String orderAttribute, boolean reverse) {
		super(builder);
		setOrder(orderAttribute, reverse);
		playerOrder = Arrays.copyOf(newOrder, newOrder.length);
	}

	public void setOrder(String attribute, boolean reverse) {
		Comparator<Player> comparator = Comparator.comparing(p -> game.getModifiedPlayerAttribute(p, attribute));
		if (reverse) {
			comparator = comparator.reversed();
		}
		newOrder = players.stream().sorted(comparator).mapToInt(p -> players.indexOf(p)).toArray();
	}

	public void setOrder(List<Player> newPlayers) {
		newOrder = newPlayers.stream().mapToInt(players::indexOf).toArray();
	}

	@Override
	public List<Player> getPlayersInOrder() {
		return IntStream.of(playerOrder).mapToObj(players::get).toList();
	}

	@Override
	public Player getCurrentPlayer() {
		return players.get(playerOrder[currentPlayer]);
	}

	@Override
	public Player getFirstPlayer() {
		return players.get(playerOrder[0]);
	}

	@Override
	public boolean nextTurn() {
		boolean endRound = super.nextTurn();
		if (endRound) {
			playerOrder = Arrays.copyOf(newOrder, newOrder.length);
		}
		return endRound;
	}
}
