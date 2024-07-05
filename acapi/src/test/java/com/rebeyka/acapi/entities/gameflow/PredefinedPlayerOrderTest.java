package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.SimpleIntegerAttribute;

public class PredefinedPlayerOrderTest {

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

	private PredefinedPlayerOrder playerOrder;

	@BeforeEach
	public void setup() {
		openMocks(this);

		doReturn(new SimpleIntegerAttribute(10)).when(game).getModifiedPlayerAttribute(player1, "VP");
		doReturn(new SimpleIntegerAttribute(20)).when(game).getModifiedPlayerAttribute(player2, "VP");
		doReturn(new SimpleIntegerAttribute(5)).when(game).getModifiedPlayerAttribute(player3, "VP");
		players = Arrays.asList(player1, player2, player3);
	}

	@Test
	public void testPlayersCreatedInOrder() {
		playerOrder = new PredefinedPlayerOrder(game, players, true, "VP", true);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player2);
		assertThat(playerOrder.endTurn()).isEqualTo(false);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player1);
		assertThat(playerOrder.endTurn()).isEqualTo(false);
		assertThat(playerOrder.getCurrentPlayer()).isEqualTo(player3);
		assertThat(playerOrder.endTurn()).isEqualTo(true);
	}

	@Test
	public void testPlayerOrderChangeEndOfTurn() {
		playerOrder = new PredefinedPlayerOrder(game, players, true, "VP", false);
		playerOrder.setOrder(players);
		assertThat(playerOrder.getPlayersInOrder()).containsExactly(player3, player1, player2);
		playerOrder.endTurn();
		playerOrder.endTurn();
		playerOrder.endTurn();
		assertThat(playerOrder.getPlayersInOrder()).containsExactly(player1, player2, player3);
	}
}
