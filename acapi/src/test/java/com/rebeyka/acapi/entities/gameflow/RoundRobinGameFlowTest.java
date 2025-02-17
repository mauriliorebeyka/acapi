package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;

public class RoundRobinGameFlowTest {

	@Mock
	private Game game;

	@Mock
	private Player player1;

	@Mock
	private Player player2;

	@Mock
	private Player player3;

	private List<Player> players;

	private RoundRobinGameFlow playerOrder;

	private GameFlowBuilder builder;

	@BeforeEach
	public void before() {
		openMocks(this);

		players = Arrays.asList(player1, player2, player3);
		when(game.getPlayers()).thenReturn(players);
		builder = new GameFlowBuilder(game);
		builder.withStaggerNewRound(false).withFirstPlayerPolicy(new SamePlayerFirstPlayerPolicy());
		playerOrder = new RoundRobinGameFlow(builder);
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
		assertThat(playerOrder.nextTurn()).isEqualTo(false);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player2);
		assertThat(playerOrder.isPlayerActive(player1)).isEqualTo(false);
		assertThat(playerOrder.isPlayerActive(player2)).isEqualTo(true);
	}

	@Test
	public void testFullRound() {
		assertThat(playerOrder.nextTurn()).isEqualTo(false);
		assertThat(playerOrder.nextTurn()).isEqualTo(false);
		assertThat(playerOrder.nextTurn()).isEqualTo(true);
		assertThat(playerOrder.getRound()).isEqualTo(2);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player1);
		assertThat(playerOrder.isPlayerActive(player1)).isEqualTo(true);
		assertThat(playerOrder.isPlayerActive(player2)).isEqualTo(false);
		assertThat(playerOrder.isPlayerActive(player3)).isEqualTo(false);

	}

	@Test
	public void testNewRounNextPolicy() {
		builder.withStaggerNewRound(true).withFirstPlayerPolicy(new NextPlayerFirstPlayerPolicy());
		playerOrder = new RoundRobinGameFlow(builder);
		playerOrder.nextRound();
		playerOrder.nextTurn();
		assertThat(playerOrder.getPlayersInOrder()).containsExactly(player2, player3, player1);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player3);
	}

	@Test
	public void testNewRoundStaggered() {
		builder.withStaggerNewRound(true).withFirstPlayerPolicy(new RandomPlayerFirstPlayerPolicy());
		playerOrder = new RoundRobinGameFlow(builder);
		for (int i = 0; i < 3; i++) {
			playerOrder.nextTurn();
		}
		assertThat(playerOrder.getCurrentPlayer()).isNull();
	}

	@Test
	public void testNextNewRoundNotStaggered() {
		builder.withStaggerNewRound(false).withFirstPlayerPolicy(new NextPlayerFirstPlayerPolicy());
		playerOrder = new RoundRobinGameFlow(builder);
		for (int i = 0; i < 3; i++) {
			assertThat(playerOrder.getFirstPlayer()).isEqualTo(player1);
			playerOrder.nextTurn();
		}
		for (int i = 0; i < 3; i++) {
			assertThat(playerOrder.getFirstPlayer()).isEqualTo(player2);
			playerOrder.nextTurn();
		}
		for (int i = 0; i < 3; i++) {
			assertThat(playerOrder.getFirstPlayer()).isEqualTo(player3);
			playerOrder.nextTurn();
		}
	}

	@Test
	public void testNewRoundRandomPolicy() {
		List<Player> randomPlayers = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			randomPlayers.add(mock(Player.class));
		}
		when(game.getPlayers()).thenReturn(randomPlayers);
		builder = new GameFlowBuilder(game);
		builder.withStaggerNewRound(true).withFirstPlayerPolicy(new RandomPlayerFirstPlayerPolicy());
		playerOrder = new RoundRobinGameFlow(builder);
		playerOrder.nextRound();
		assertThat(playerOrder.getCurrentPlayer()).isNotEqualTo(randomPlayers.get(0));
		assertThat(playerOrder.getPlayersInOrder()).containsAll(randomPlayers).hasSize(1000);
	}

	@Test
	public void testNewRoundSamePolicy() {
		builder.withStaggerNewRound(true).withFirstPlayerPolicy(new SamePlayerFirstPlayerPolicy());
		playerOrder = new RoundRobinGameFlow(builder);
		playerOrder.nextRound();
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player1);
		assertThat(playerOrder.getPlayersInOrder()).containsExactly(player1, player2, player3);
	}

	@Test
	public void testMultiplePhases() {
		playerOrder = new RoundRobinGameFlow(builder.withGamePhases(Arrays.asList("DRAW", "MAIN")));
		assertThat(playerOrder.getCurrentGamePhase()).isEqualTo("DRAW");
		playerOrder.nextPhase();
		assertThat(playerOrder.getCurrentGamePhase()).isEqualTo("MAIN");
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player1);
		playerOrder.nextPhase();
		assertThat(playerOrder.getCurrentGamePhase()).isEqualTo("DRAW");
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player2);
	}
}
