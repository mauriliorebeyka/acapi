package com.rebeyka.acapi.check;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.Types;
import com.rebeyka.acapi.entities.gameflow.GameFlow;

public class PlayableCheckTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testActivePlayer() {
		Player player = mock(Player.class);
		Player another = mock(Player.class);
		Game game = mock(Game.class);
		GameFlow gameflow = mock(GameFlow.class);
		when(game.getGameFlow()).thenReturn(gameflow);
		when(gameflow.getCurrentPlayer()).thenReturn(player);
		when(gameflow.isPlayerActive(player)).thenReturn(true);
		when(player.getGame()).thenReturn(game);
		
		Attribute attribute = mock(Attribute.class);
		when(player.getAttribute("title")).thenReturn(attribute);
		when(attribute.getValue()).thenReturn("A TITLE");
		when(game.getModifiedAttribute(player, player.getRawAttribute("title", Types.string())))
				.thenReturn(attribute);
		
		PlayableCheck checker = Checker.whenPlayable();
		checker = checker.not().isExactly(another).attribute("title").asString().isEqualsTo("A TITLE").not().attribute("title").asString().isEqualsTo("TITLE").isActivePlayer();
		assertThat(checker.check(player)).isTrue();
	}

	@Test
	public void testAnyOf() {
		BacktrackingPlayableCheck<Playable,?> always = Checker.whenPlayable().always();
		BacktrackingPlayableCheck<Playable,?> never = Checker.whenPlayable().not().always();
		assertThat(Checker.whenPlayable().anyOf(always).check(mock(Playable.class))).isTrue();
		assertThat(Checker.whenPlayable().anyOf(never).check(null)).isFalse();
		Checker.whenPlayable().attribute("").asString().anyOf(Checker.whenString().contains(""));
		assertThat(Checker.whenPlayable().anyOf(always, never).check(null)).isTrue();
	}
}
