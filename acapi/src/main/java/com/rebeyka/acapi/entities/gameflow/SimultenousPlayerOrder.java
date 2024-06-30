package com.rebeyka.acapi.entities.gameflow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class SimultenousPlayerOrder extends PlayerOrder {

	private boolean[] endedTurnPlayers;

	public SimultenousPlayerOrder(Game game, List<Player> players, boolean staggerNewRound,
			FirstPlayerPolicy firstPlayerPolicy) {
		super(game, players, staggerNewRound, firstPlayerPolicy);

		endedTurnPlayers = new boolean[players.size()];
	}

	public SimultenousPlayerOrder(Game game, List<Player> players) {
		this(game, players, true, FirstPlayerPolicy.SAME);
	}

	private void reset() {
		Arrays.fill(endedTurnPlayers, false);
	}

	@Override
	public List<Player> getPlayersInOrder() {
		return players;
	}

	@Override
	public Player getCurrentPlayer() {
		if (allPlayersFinished()) {
			return null;
		} else {
			return players.get(firstPlayer);
		}
	}

	@Override
	public boolean isPlayerActive(Player player) {
		return !endedTurnPlayers[players.indexOf(player)];
	}

	@Override
	public boolean endTurn() {
		if (allPlayersFinished()) {
			if (!staggerNewRound) {
				reset();
				newRound();
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean endTurn(Player player) {
		endedTurnPlayers[players.indexOf(player)] = true;
		return endTurn();
	}

	private boolean allPlayersFinished() {
		return IntStream.range(0, endedTurnPlayers.length).mapToObj(b -> endedTurnPlayers[b]).allMatch(b -> b == true);
	}
}
