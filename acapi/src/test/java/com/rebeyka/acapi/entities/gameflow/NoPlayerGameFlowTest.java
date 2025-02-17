package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class NoPlayerGameFlowTest {

	@Test
	public void testInactivePlayerOrder() {
		List<Player> players = Arrays.asList(mock(Player.class));
		Game game = mock(Game.class);
		when(game.getPlayers()).thenReturn(players);
		GameFlowBuilder builder = new GameFlowBuilder(game);
		NoPlayerGameFlow playerOrder = new NoPlayerGameFlow(builder);
		assertThat(playerOrder.isPlayerActive(players.get(0))).isFalse();
		assertThat(playerOrder.getPlayersInOrder()).isEqualTo(players);
		assertThat(playerOrder.getFirstPlayer()).isEqualTo(players.get(0));
		assertThat(playerOrder.getCurrentPlayer()).isNull();
		assertThat(playerOrder.nextTurn()).isTrue();
	}
}
