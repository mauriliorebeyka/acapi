package com.rebeyka.acapi.check;

import static com.rebeyka.acapi.check.Checker.whenActionable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Play;

public class TimelineCheckTest {

	@Test
	public void testTimeline() {
		Game game = mock(Game.class);
		when(game.countActionables("actionable", "")).thenReturn(1);
		Actionable actionable = mock(Actionable.class);
		Play play = mock(Play.class);
		when(actionable.getParent()).thenReturn(play);
		when(play.getGame()).thenReturn(game);
		when(actionable.getActionableId()).thenReturn("actionable");
		Actionable actionable2 = mock(Actionable.class);
		when(actionable2.getActionableId()).thenReturn("test");
		assertThat(whenActionable().id().sameValue("actionable").happened().sinceStart().check(actionable)).isTrue();
	}
}
