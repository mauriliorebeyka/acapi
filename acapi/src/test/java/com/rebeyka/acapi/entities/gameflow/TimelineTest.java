package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.ChoiceActionable;
import com.rebeyka.acapi.actionables.CostActionable;
import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;

public class TimelineTest {

	private Timeline timeline;

	private Play.Builder builder;

	@Mock
	private Game game;

	@BeforeEach
	public void before() {
		openMocks(this);
		Cost mockCost = mock(Cost.class);
		CostActionable mockCostActionable = mock(CostActionable.class);
		when(mockCost.getCostActionable()).thenReturn(mockCostActionable);
		when(mockCostActionable.getParent()).thenReturn(mock(Play.class));
		when(mockCostActionable.isSet()).thenReturn(true);

		timeline = new Timeline(game);
		builder = new Play.Builder().name("id").origin(mock(Playable.class)).cost(mockCost);
	}

	@Test
	public void testQueue() {
		Playable mockPlayable = mock(Playable.class);
		when(mockPlayable.getGame()).thenReturn(mock(Game.class));
		Actionable mockActionable1 = mock(Actionable.class);
		Actionable mockActionable2 = mock(Actionable.class);
		Play play1 = builder.origin(mockPlayable)
				.actionables(Arrays.asList(mockActionable1, mockActionable2)).build();
		when(mockActionable1.getParent()).thenReturn(play1);
		when(mockActionable1.supply()).thenReturn(() -> mockActionable1);
		when(mockActionable2.getParent()).thenReturn(play1);
		when(mockActionable2.supply()).thenReturn(() -> mockActionable2);

		timeline.queue(play1);
		timeline.executeNext();
		timeline.executeNext();
		verify(mockActionable1).execute();
		verify(mockActionable1).supply();
		verify(mockActionable2, never()).execute();
		verify(mockActionable2).supply();
	}

	@Test
	public void TestQueuePayCost() {
		Actionable mockActionable1 = mock(Actionable.class);
		when(mockActionable1.supply()).thenReturn(() -> mockActionable1);
		Cost mockCost = mock(Cost.class);
		when(mockCost.getCostActionable()).thenReturn(mock(CostActionable.class));
		Play play1 = builder.actionables(Arrays.asList(mockActionable1)).cost(mockCost).build();

		timeline.queue(play1);
	}

	@Test
	public void testTimelineLimits() {
		assertThatNoException().isThrownBy(() -> timeline.executeNext());
		assertThatNoException().isThrownBy(() -> timeline.rollbackLast());
	}

	@Test
	public void testRollback() {
		Actionable mockActionable1 = mock(Actionable.class);
		Play script1 = builder.actionables(Arrays.asList(mockActionable1)).build();
		when(mockActionable1.getParent()).thenReturn(script1);
		when(mockActionable1.supply()).thenReturn(() -> mockActionable1);

		timeline.queue(script1);
		timeline.executeNext();
		timeline.rollbackLast();
		timeline.executeNext();
	}

	@Test
	public void testCancelHasFuturePlay() {
		Actionable mockActionable1 = mock(Actionable.class);
		Actionable mockActionable2 = mock(Actionable.class);
		Actionable mockActionable3 = mock(Actionable.class);
		Actionable mockActionable4 = mock(Actionable.class);
		Actionable mockActionable5 = mock(Actionable.class);
		Actionable mockActionable6 = mock(Actionable.class);

		Play play1 = builder.actionables(Arrays.asList(mockActionable1)).build();
		when(mockActionable1.getParent()).thenReturn(play1);
		when(mockActionable1.supply()).thenReturn(() -> mockActionable1);
		Play play2 = builder
				.actionables(Arrays.asList(mockActionable2, mockActionable3, mockActionable4))
				.build();
		when(mockActionable2.getParent()).thenReturn(play2);
		when(mockActionable2.supply()).thenReturn(() -> mockActionable2);
		when(mockActionable3.getParent()).thenReturn(play2);
		when(mockActionable3.supply()).thenReturn(() -> mockActionable3);
		when(mockActionable4.getParent()).thenReturn(play2);
		when(mockActionable4.supply()).thenReturn(() -> mockActionable4);
		Play play3 = builder.actionables(Arrays.asList(mockActionable5, mockActionable6)).build();
		when(mockActionable5.getParent()).thenReturn(play3);
		when(mockActionable5.supply()).thenReturn(() -> mockActionable5);
		when(mockActionable6.getParent()).thenReturn(play3);
		when(mockActionable6.supply()).thenReturn(() -> mockActionable6);

		timeline.queue(play1);
		timeline.queue(play2);
		timeline.queue(play3);

		timeline.executeNext(); // Play1 Cost
		timeline.executeNext(); // Actionable 1
		timeline.executeNext(); // Play2 Cost
		timeline.executeNext(); // Actionable 2
		timeline.cancelCurrentPlay();
		timeline.executeNext();
		timeline.executeNext();

		verify(mockActionable1).execute();
		verify(mockActionable1).getMessage();
		verify(mockActionable1).getParent();
		verify(mockActionable1).getActionableId();
		verify(mockActionable1).supply();
		verify(mockActionable1).setParent(play1);
		
		verify(mockActionable2).execute();
		verify(mockActionable2).getMessage();
		verify(mockActionable2).getActionableId();
		verify(mockActionable2, times(3)).getParent();
		verify(mockActionable2).rollback();
		verify(mockActionable2).supply();
		verify(mockActionable2).setParent(play2);
		
		verify(mockActionable3).supply();
		verify(mockActionable3, times(2)).getParent();
		verify(mockActionable3).supply();
		verify(mockActionable3).setParent(play2);
		
		verify(mockActionable4).getParent();
		verify(mockActionable4).supply();
		verify(mockActionable4).setParent(play2);
		
		verify(mockActionable5).execute();
		verify(mockActionable5).getMessage();
		verify(mockActionable5).getParent();
		verify(mockActionable5).getActionableId();
		verify(mockActionable5).supply();
		verify(mockActionable5).setParent(play3);
		verify(mockActionable6).supply();
		verify(mockActionable6).setParent(play3);
		verifyNoMoreInteractions(mockActionable1, mockActionable2, mockActionable3, mockActionable4, mockActionable5,
				mockActionable6);
		assertThat(timeline.getCurrent()).isEqualTo(mockActionable6);
	}

	@Test
	public void testCancelPlayCompleted() {
		Actionable mockActionable1 = mock(Actionable.class);
		builder.actionables(Arrays.asList(mockActionable1));
		Play play1 = builder.build();
		when(mockActionable1.getParent()).thenReturn(play1);
		when(mockActionable1.supply()).thenReturn(() -> mockActionable1);

		timeline.queue(play1);
		timeline.executeNext();
		timeline.executeNext();
		timeline.cancelCurrentPlay();

		verify(mockActionable1).execute();
		verify(mockActionable1).supply();
		verify(mockActionable1).setParent(play1);
		verify(mockActionable1).getMessage();
		verify(mockActionable1).getParent();
		verify(mockActionable1).getActionableId();
		verifyNoMoreInteractions(mockActionable1);
		assertThat(timeline.getCurrent()).isNull();
	}

	@Test
	public void testCancelPlayNoFurtherActionable() {
		Actionable mockActionable1 = mock(Actionable.class);
		Actionable mockActionable2 = mock(Actionable.class);
		Play play1 = builder.actionables(Arrays.asList(mockActionable1, mockActionable2)).build();
		when(mockActionable1.getParent()).thenReturn(play1);
		when(mockActionable1.supply()).thenReturn(() -> mockActionable1);
		when(mockActionable2.getParent()).thenReturn(play1);
		when(mockActionable2.supply()).thenReturn(() -> mockActionable2);

		timeline.queue(play1);
		timeline.executeNext();
		timeline.executeNext();
		timeline.cancelCurrentPlay();
		timeline.executeNext();

		verify(mockActionable1).execute();
		verify(mockActionable1).getMessage();
		verify(mockActionable1).getActionableId();
		verify(mockActionable1).rollback();
		verify(mockActionable1, times(3)).getParent();
		verify(mockActionable1).supply();
		verify(mockActionable1).setParent(play1);
		verify(mockActionable2, times(2)).getParent();
		verify(mockActionable2).supply();
		verify(mockActionable2).setParent(play1);
		verifyNoMoreInteractions(mockActionable1, mockActionable2);
		assertThat(timeline.getCurrent()).isNull();
	}

	@Test
	public void testPlayWithCost() {
		Actionable mockActionable1 = mock(Actionable.class);
		CostActionable mockCostActionable = mock(CostActionable.class);
		Actionable mockPayingCostActionable = mock(Actionable.class);
		Cost mockCost = mock(Cost.class);
		when(mockCost.getCostActionable()).thenReturn(mockCostActionable);
		when(mockCostActionable.isSet()).thenReturn(true);
		when(mockActionable1.supply()).thenReturn(() -> mockActionable1);
		when(mockPayingCostActionable.supply()).thenReturn(() -> mockPayingCostActionable);
		Play play1 = builder.cost(mockCost).actionables(Arrays.asList(mockActionable1)).build();
		Play costPlay = builder.cost(null).actionable(mockPayingCostActionable).build();
		when(mockCostActionable.getCostPlays()).thenReturn(List.of(costPlay));

		timeline.queue(play1);
		timeline.executeNext();
		timeline.executeNext();
		timeline.executeNext();
		verify(mockCostActionable).execute();
		verify(mockCostActionable).getMessage();
		verify(mockCostActionable).getParent();
		verify(mockCostActionable).getActionableId();
		verify(mockCostActionable).isSet();
		verify(mockCostActionable).getCostPlays();
		verify(mockCostActionable, times(2)).setParent(play1);
		verify(mockActionable1).execute();
		verify(mockActionable1).getMessage();
		verify(mockActionable1).getParent();
		verify(mockActionable1).getActionableId();
		verify(mockActionable1).supply();
		verify(mockActionable1).setParent(play1);
		verify(mockPayingCostActionable).execute();
		verifyNoMoreInteractions(mockCostActionable, mockActionable1);
	}

	@Test
	public void testPlayCostNotPaid() {
		Actionable mockActionable1 = mock(Actionable.class);
		CostActionable mockCostActionable = mock(CostActionable.class);
		Cost mockCost = mock(Cost.class);
		when(mockCost.getCostActionable()).thenReturn(mockCostActionable);
		Play play1 = builder.cost(mockCost).actionables(Arrays.asList(mockActionable1)).cost(mockCost).build();
		when(mockCostActionable.getParent()).thenReturn(play1);
		when(mockCostActionable.supply()).thenReturn(() -> mockCostActionable);
		when(mockActionable1.getParent()).thenReturn(play1);
		when(mockActionable1.supply()).thenReturn(() -> mockActionable1);

		timeline.queue(play1);
		timeline.executeNext();
		verify(mockCostActionable).isSet();
		verify(mockCostActionable, times(2)).getParent();
		verify(mockCostActionable).getActionableId();
		verify(mockCostActionable, times(2)).setParent(play1);
		verify(mockActionable1).getParent();
		verify(mockActionable1).supply();
		verify(mockActionable1).setParent(play1);
		verifyNoMoreInteractions(mockActionable1, mockCostActionable);
	}

	@Test
	public void testChoiceActionable() {
		ChoiceActionable mockActionable1 = mock(ChoiceActionable.class);
		ChoiceActionable mockActionable2 = mock(ChoiceActionable.class);
		when(mockActionable1.isSet()).thenReturn(true);
		when(mockActionable1.supply()).thenReturn(() -> mockActionable1);
		when(mockActionable2.supply()).thenReturn(() -> mockActionable2);
		Play play1 = builder.actionables(Arrays.asList(mockActionable1, mockActionable2)).build();

		timeline.queue(play1);
		timeline.executeNext();
		timeline.executeNext();
		timeline.executeNext();
		verify(mockActionable1).isSet();
		verify(mockActionable1).supply();
		verify(mockActionable1).setParent(play1);
		verify(mockActionable1).execute();
		verify(mockActionable1).getActionableId();
		verify(mockActionable1).getMessage();
		verify(mockActionable1).getParent();
		verify(mockActionable2).isSet();
		verify(mockActionable2).getActionableId();
		verify(mockActionable2).supply();
		verify(mockActionable2).setParent(play1);
		verifyNoMoreInteractions(mockActionable1, mockActionable2);
	}

	@Test
	public void testGameTriggersOrder() {
		Actionable mockActionable1 = mock(Actionable.class);
		when(mockActionable1.getActionableId()).thenReturn("mockActionable1");
		when(mockActionable1.supply()).thenReturn(() -> mockActionable1);
		Actionable mockActionable2 = mock(Actionable.class);
		when(mockActionable2.getActionableId()).thenReturn("mockActionable2");
		when(mockActionable2.supply()).thenReturn(() -> mockActionable2);
		Actionable beforeActionable2 = mock(Actionable.class);
		when(beforeActionable2.getActionableId()).thenReturn("beforeActionable2");
		when(beforeActionable2.supply()).thenReturn(() -> beforeActionable2);
		Actionable afterActionable1 = mock(Actionable.class);
		when(afterActionable1.getActionableId()).thenReturn("afterActionable1");
		when(afterActionable1.supply()).thenReturn(() -> afterActionable1);
		Play play1 = builder.cost(null).actionables(Arrays.asList(mockActionable1, mockActionable2))
				.build();
		Play play2 = builder.actionable(beforeActionable2).build();
		Play play3 = builder.actionable(afterActionable1).build();
		when(game.getBeforeTriggerActionables(mockActionable2)).thenReturn(List.of(play2)).thenReturn(Collections.emptyList());
		when(game.getAfterTriggerActionables(mockActionable1)).thenReturn(List.of(play3));

		timeline.queue(play1);
		timeline.executeNext();
		timeline.executeNext();
		timeline.executeNext();
		timeline.executeNext();
		verify(game).getBeforeTriggerActionables(beforeActionable2);
		verify(game,times(2)).getBeforeTriggerActionables(mockActionable2);
		verify(game).getAfterTriggerActionables(mockActionable1);
		verify(game).getAfterTriggerActionables(afterActionable1);
		verify(game).getAfterTriggerActionables(beforeActionable2);
		verify(game).getAfterTriggerActionables(mockActionable2);

		assertThat(timeline.executeNext()).isFalse();
		assertThat(timeline.getExecutedActionables()).containsExactly(mockActionable1, afterActionable1,
				beforeActionable2, mockActionable2);
		verifyNoMoreInteractions(game);
	}
}
