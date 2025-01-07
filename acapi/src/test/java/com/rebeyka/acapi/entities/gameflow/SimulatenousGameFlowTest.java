package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.gameflow.GameFlow.FirstPlayerPolicy;

public class SimulatenousGameFlowTest {

	@Mock
	private Game game;

	@Mock
	private Player player1;

	@Mock
	private Player player2;

	@Mock
	private Player player3;

	private List<Player> players;

	private SimultaneousGameFlow playerOrder;

	private GameFlowBuilder builder;

	@BeforeEach
	public void setup() {
		openMocks(this);

		players = Arrays.asList(player1, player2, player3);

		builder = new GameFlowBuilder().withGame(game).withPlayers(players)
				.withFirstPlayerPolicy(FirstPlayerPolicy.NEXT);
	}

	@Test
	public void testEndTurn() {
		playerOrder = new SimultaneousGameFlow(builder.withStaggerNewRound(false));
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player1);
		assertThat(playerOrder.isPlayerActive(player1)).isTrue();
		assertThat(playerOrder.isPlayerActive(player2)).isTrue();
		assertThat(playerOrder.isPlayerActive(player3)).isTrue();
		assertThat(playerOrder.endTurn(player1)).isFalse();
		assertThat(playerOrder.endTurn(player2)).isFalse();
		assertThat(playerOrder.isPlayerActive(player1)).isFalse();
		assertThat(playerOrder.isPlayerActive(player2)).isFalse();
		assertThat(playerOrder.isPlayerActive(player3)).isTrue();
		assertThat(playerOrder.endTurn(player3)).isTrue();
		assertThat(playerOrder.getRound()).isEqualTo(2);
		assertThat(playerOrder.isPlayerActive(player1)).isTrue();
		assertThat(playerOrder.isPlayerActive(player2)).isTrue();
		assertThat(playerOrder.isPlayerActive(player3)).isTrue();
	}

	@Test
	public void testNewRound() {
		playerOrder = new SimultaneousGameFlow(builder.withStaggerNewRound(true));
		assertThat(playerOrder.getPlayersInOrder()).containsExactly(player1, player2, player3);
		assertThat(playerOrder.endTurn(player1)).isFalse();
		assertThat(playerOrder.endTurn(player2)).isFalse();
		assertThat(playerOrder.endTurn(player3)).isTrue();
		assertThat(playerOrder.getCurrentPlayer()).isNull();
	}
}
