package com.rebeyka.acapi.check;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.Types;
import com.rebeyka.acapi.entities.gameflow.GameFlow;
import java.util.List;

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
		player.getRawAttribute("title", Types.string()).setValue("A TITLE");
		when(game.getModifiedAttribute(player, player.getRawAttribute("title", Types.string())))
				.thenReturn(player.getRawAttribute("title", Types.string()));
		PlayableCheck<Playable, PlayableCheck<Playable,?>> checker = Checker.whenPlayable();
		checker.not().isExactly(another).attribute("title").asString().isEqualsTo("A TITLE").attribute("title").asString().not().isEqualsTo("TITLE").isActivePlayer();
		assertThat(checker.check(player)).isTrue();
	}

	@Test
	public void testAnyOf() {
		PlayableCheck<Playable,PlayableCheck<Playable,?>> always = Checker.whenPlayable().always();
		PlayableCheck<Playable,PlayableCheck<Playable,?>> never = Checker.whenPlayable().not().always();
		assertThat(Checker.whenPlayable().anyOf(List.of(always)).check(null)).isTrue();
		assertThat(Checker.whenPlayable().anyOf(List.of(never)).check(null)).isFalse();
		assertThat(Checker.whenPlayable().anyOf(List.of(always, never)).check(null)).isTrue();
	}
}
