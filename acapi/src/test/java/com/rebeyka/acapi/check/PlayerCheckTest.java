package com.rebeyka.acapi.check;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.BasePlayable;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.GameEntityFinder;
import com.rebeyka.acapi.entities.PlayArea;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.gameflow.GameFlow;

public class PlayerCheckTest {

	@Test
	public void test() {
		Game game = mock(Game.class);
		GameFlow gameFlow = mock(GameFlow.class);
		GameEntityFinder finder = mock(GameEntityFinder.class);
		PlayArea playArea = mock(PlayArea.class);
		Player player1 = mock(Player.class);
		BasePlayable playable = mock(BasePlayable.class);
		
		when(game.getGameFlow()).thenReturn(gameFlow);
		when(game.find()).thenReturn(finder);
		when(gameFlow.isCurrentPlayer(player1)).thenReturn(true);
		when(gameFlow.isPlayerActive(player1)).thenReturn(true);
		when(player1.getId()).thenReturn("ID");
		when(finder.playArea(playable)).thenReturn(playArea);
		when(playArea.getOwner()).thenReturn(player1);
		when(playable.getGame()).thenReturn(game);
		when(player1.getGame()).thenReturn(game);
		
		
		PlayerCheck<Playable> checker = Checker.whenPlayable().controller();
		assertThat(checker.is(player1).check(playable)).isTrue();
		assertThat(checker.id().sameValueAs("ID").check(playable)).isTrue();
		assertThat(checker.isCurrentPlayer().check(playable)).isTrue();
		assertThat(checker.isActivePlayer().check(playable)).isTrue();
	}
}
