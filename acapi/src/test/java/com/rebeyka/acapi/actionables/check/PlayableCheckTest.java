package com.rebeyka.acapi.actionables.check;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.gameflow.GameFlow;

public class PlayableCheckTest {

	@Test
	public void testActivePlayer() {
		Player player = new Player("player");
		Player another = new Player("player");
		Game game = mock(Game.class);
		GameFlow gameflow = mock(GameFlow.class);
		when(game.getGameFlow()).thenReturn(gameflow);
		when(gameflow.getCurrentPlayer()).thenReturn(player);
		when(gameflow.isPlayerActive(player)).thenReturn(true);
		player.setGame(game);
		player.setAttribute("title", new Attribute<String>("A TITLE"));
		PlayableChecker checker = Checker.whenPlayable();
		checker.not().is(another).attribute("title").is("A TITLE").not().sameValue("TITLE").isActivePlayer();
		assertThat(checker.check(player)).isTrue();
	}
}
