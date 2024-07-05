package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class RoundRobinPlayerOrderTest {

	@Mock
	private Game game;

	@Mock
	private Player player1;

	@Mock
	private Player player2;

	@Mock
	private Player player3;

	private List<Player> players;

	private RoundRobinPlayerOrder playerOrder;

	@BeforeEach
	public void before() {
		openMocks(this);

		players = Arrays.asList(player1, player2, player3);
		playerOrder = new RoundRobinPlayerOrder(game, players);
	}

	@Test
	public void testInitialState() {
		assertThat(playerOrder.getRound()).isEqualTo(1);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player1);
		assertThat(playerOrder.isPlayerActive(player1)).isEqualTo(true);
		assertThat(playerOrder.isPlayerActive(player2)).isEqualTo(false);
		assertThat(playerOrder.isPlayerActive(player3)).isEqualTo(false);
	}

	@Test
	public void testNextPlayer() {
		assertThat(playerOrder.endTurn()).isEqualTo(false);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player2);
		assertThat(playerOrder.isPlayerActive(player1)).isEqualTo(false);
		assertThat(playerOrder.isPlayerActive(player2)).isEqualTo(true);
	}

	@Test
	public void testFullRound() {
		assertThat(playerOrder.endTurn()).isEqualTo(false);
		assertThat(playerOrder.endTurn()).isEqualTo(false);
		assertThat(playerOrder.endTurn()).isEqualTo(true);
		assertThat(playerOrder.getRound()).isEqualTo(2);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player1);
		assertThat(playerOrder.isPlayerActive(player1)).isEqualTo(true);
		assertThat(playerOrder.isPlayerActive(player2)).isEqualTo(false);
		assertThat(playerOrder.isPlayerActive(player3)).isEqualTo(false);
	}

	@Test
	public void testNewRounNextPolicy() {
		playerOrder = new RoundRobinPlayerOrder(game, players, true, PlayerOrder.FirstPlayerPolicy.NEXT);
		playerOrder.newRound();
		playerOrder.endTurn();
		assertThat(playerOrder.getPlayersInOrder()).containsExactly(player2, player3, player1);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player3);
	}

	@Test
	public void testNewRoundStaggered() {
		playerOrder = new RoundRobinPlayerOrder(game, players, true, PlayerOrder.FirstPlayerPolicy.RANDOM);
		for (int i = 0; i < 3; i++) {
			playerOrder.endTurn();
		}
		assertThat(playerOrder.getCurrentPlayer()).isNull();
	}

	@Test
	public void testNewRoundRandomPolicy() {
		List<Player> randomPlayers = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			randomPlayers.add(mock(Player.class));
		}

		playerOrder = new RoundRobinPlayerOrder(game, randomPlayers, true, PlayerOrder.FirstPlayerPolicy.RANDOM);
		playerOrder.newRound();
		assertThat(playerOrder.getCurrentPlayer()).isNotEqualTo(randomPlayers.get(0));
		assertThat(playerOrder.getPlayersInOrder()).containsAll(randomPlayers).hasSize(1000);
	}

	@Test
	public void testNewRoundSamePolicy() {
		playerOrder = new RoundRobinPlayerOrder(game, players, true, PlayerOrder.FirstPlayerPolicy.SAME);
		playerOrder.newRound();
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player1);
		assertThat(playerOrder.getPlayersInOrder()).containsExactly(player1, player2, player3);
	}
}
