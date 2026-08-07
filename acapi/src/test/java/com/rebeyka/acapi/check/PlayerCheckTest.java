package com.rebeyka.acapi.check;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.gameflow.GameFlow;

public class PlayerCheckTest {

	@Test
	public void test() {
		Game game = mock(Game.class);
		GameFlow gameFlow = mock(GameFlow.class);
		Player player1 = mock(Player.class);
		
		when(game.getGameFlow()).thenReturn(gameFlow);
		when(gameFlow.isCurrentPlayer(player1)).thenReturn(true);
		when(gameFlow.isPlayerActive(player1)).thenReturn(true);
		when(player1.getId()).thenReturn("ID");
		when(player1.getGame()).thenReturn(game);
		
		
		PlayerCheck checker = Checker.whenPlayer();
		assertThat(checker.isExactly(player1).check(player1)).isTrue();
		assertThat(checker.id().isEqualsTo("ID").check(player1)).isTrue();
		assertThat(checker.isCurrentPlayer().check(player1)).isTrue();
		assertThat(checker.isActivePlayer().check(player1)).isTrue();
	}
}
