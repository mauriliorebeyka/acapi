package com.rebeyka.acapi.entities.gameflow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.Player;

public class SimultaneousGameFlow extends GameFlow {

	private boolean[] endedTurnPlayers;

	public SimultaneousGameFlow(GameFlowBuilder builder) {
		super(builder);

		endedTurnPlayers = new boolean[players.size()];
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
	public boolean nextTurn() {
		if (allPlayersFinished()) {
			if (!staggerNewRound) {
				reset();
				nextRound();
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean endTurn(Player player) {
		endedTurnPlayers[players.indexOf(player)] = true;
		return nextTurn();
	}

	private boolean allPlayersFinished() {
		return IntStream.range(0, endedTurnPlayers.length).mapToObj(b -> endedTurnPlayers[b]).allMatch(b -> b == true);
	}
}
