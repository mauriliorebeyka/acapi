package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class InactivePlayerOrderTest {

	@Test
	public void testInactivePlayerOrder() {
		List<Player> players = Arrays.asList(mock(Player.class));
		InactivePlayerOrder playerOrder = new InactivePlayerOrder(mock(Game.class), players);
		assertThat(playerOrder.isPlayerActive(players.get(0))).isFalse();
		assertThat(playerOrder.getPlayersInOrder()).isEqualTo(players);
		assertThat(playerOrder.getFirstPlayer()).isEqualTo(players.get(0));
		assertThat(playerOrder.getCurrentPlayer()).isNull();
		assertThat(playerOrder.endTurn()).isTrue();
	}
}
