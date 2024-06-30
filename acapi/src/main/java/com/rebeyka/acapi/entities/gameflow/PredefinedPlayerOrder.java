package com.rebeyka.acapi.entities.gameflow;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class PredefinedPlayerOrder extends RoundRobinPlayerOrder {

	private int[] playerOrder;

	private int[] newOrder;

	public PredefinedPlayerOrder(Game game, List<Player> players, boolean staggerNewRound) {
		super(game, players, staggerNewRound, FirstPlayerPolicy.SAME);
		setOrder(players);
		playerOrder = Arrays.copyOf(newOrder, newOrder.length);
	}

	public PredefinedPlayerOrder(Game game, List<Player> players, boolean staggerNewRound, String orderAttribute,
			boolean reverse) {
		this(game, players, staggerNewRound);
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
	public boolean endTurn() {
		boolean endRound = super.endTurn();
		if (endRound) {
			playerOrder = Arrays.copyOf(newOrder, newOrder.length);
		}
		return endRound;
	}
}
