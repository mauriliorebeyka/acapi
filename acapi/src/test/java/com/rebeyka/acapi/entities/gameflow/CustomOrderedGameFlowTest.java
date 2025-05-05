package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.rebeyka.acapi.builders.GameFlowBuilder;
import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.Types;

public class CustomOrderedGameFlowTest {

	@Mock
	private Game game;

	@Mock
	private Player player1;

	@Mock
	private Player player2;

	@Mock
	private Player player3;

	@Mock
	private List<Player> players;

	private CustomOrderedGameFlow playerOrder;

	private GameFlowBuilder builder;

	@BeforeEach
	public void setup() {
		openMocks(this);

		doReturn(new Attribute<Integer>("",10,Types.integer())).when(game).getModifiedPlayerAttribute(player1, "VP");
		doReturn(new Attribute<Integer>("",20,Types.integer())).when(game).getModifiedPlayerAttribute(player2, "VP");
		doReturn(new Attribute<Integer>("",5,Types.integer())).when(game).getModifiedPlayerAttribute(player3, "VP");
		players = Arrays.asList(player1, player2, player3);
		when(game.getPlayers()).thenReturn(players);

		builder = new GameFlowBuilder(game).withStaggerNewRound(true);
	}

	@Test
	public void testPlayersCreatedInOrder() {

		playerOrder = new CustomOrderedGameFlow(builder, "VP", true);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player2);
		assertThat(playerOrder.nextTurn()).isEqualTo(false);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player1);
		assertThat(playerOrder.nextTurn()).isEqualTo(false);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player3);
		assertThat(playerOrder.nextTurn()).isEqualTo(true);
	}

	@Test
	public void testPlayerOrderChangeEndOfTurn() {
		playerOrder = new CustomOrderedGameFlow(builder, "VP", false);
		playerOrder.setOrder(players);
		assertThat(playerOrder.getPlayersInOrder()).containsExactly(player3, player1, player2);
		playerOrder.nextTurn();
		playerOrder.nextTurn();
		playerOrder.nextTurn();
		assertThat(playerOrder.getPlayersInOrder()).containsExactly(player1, player2, player3);
	}
}
