package com.rebeyka.acapi.check;

import static com.rebeyka.acapi.check.Checker.whenActionable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.gameflow.EndRoundActionable;
import com.rebeyka.acapi.actionables.gameflow.EndTurnActionable;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.gameflow.Play;

public class TimelineCheckTest {
	
	@Mock
	private Game game;
	@Mock
	private Actionable actionable;
	@Mock
	private Play play;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		
		when(actionable.getParent()).thenReturn(play);
		when(play.getGame()).thenReturn(game);
		when(actionable.getActionableId()).thenReturn("actionable");
	}

	@Test
	public void testSinceStartAndIdExtraction() {
		when(game.countActionables("actionable", "")).thenReturn(1);

		assertThat(whenActionable().hasId().isEqualsTo("actionable").happened().sinceStart().check(actionable)).isTrue();
	}

	@Test
	public void testAtLeastAtMostExactly() {
		when(game.countActionables("actionable", "")).thenReturn(2);

		assertThat(whenActionable().happened().atLeast(2).sinceStart().check(actionable)).isTrue();
		assertThat(whenActionable().happened().atLeast(3).sinceStart().check(actionable)).isFalse();

		assertThat(whenActionable().happened().atMost(2).sinceStart().check(actionable)).isTrue();
		assertThat(whenActionable().happened().atMost(1).sinceStart().check(actionable)).isFalse();

		assertThat(whenActionable().happened().exactly(2).sinceStart().check(actionable)).isTrue();
		assertThat(whenActionable().happened().exactly(3).sinceStart().check(actionable)).isFalse();
	}

	@Test
	public void testOnce() {
		when(game.countActionables("actionable", "")).thenReturn(1);

		assertThat(whenActionable().happened().once().sinceStart().check(actionable)).isTrue();
		assertThat(whenActionable().happened().atMostOnce().sinceStart().check(actionable)).isTrue();
		assertThat(whenActionable().happened().atLeastOnce().sinceStart().check(actionable)).isTrue();
	}
	
	@Test
	public void testLastWithStepsBack() {
		when(game.countActionables("actionable", 3)).thenReturn(1);

		assertThat(whenActionable().happened().exactly(1).last(3).check(actionable)).isTrue();
		when(game.countActionables("actionable", 1)).thenReturn(0);
		assertThat(whenActionable().happened().exactly(0).last().check(actionable)).isTrue();
	}

	@Test
	public void testThisTurnAndThisRoundBounds() {
		when(game.countActionables("actionable", EndTurnActionable.ID)).thenReturn(2);
		when(game.countActionables("actionable", EndRoundActionable.ID)).thenReturn(3);

		assertThat(whenActionable().happened().atLeast(2).thisTurn().check(actionable)).isTrue();
		assertThat(whenActionable().happened().atLeast(3).thisRound().check(actionable)).isTrue();
	}

	@Test
	public void testExplicitActionableIdOverridesActionableInstanceId() {
		when(game.countActionables("explicitId", "")).thenReturn(5);
		when(actionable.getActionableId()).thenReturn("differentId");

		assertThat(whenActionable().happened("explicitId").exactly(5).sinceStart().check(actionable)).isTrue();
	}

}
