package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.ChoiceActionable;
import com.rebeyka.acapi.actionables.CostActionable;
import com.rebeyka.acapi.builders.PlayBuilder;
import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.FreeCost;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Playable;

public class TimelineTest {

	private Timeline timeline;

	private PlayBuilder builder;
	
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
		builder = new PlayBuilder().withId("id").withOrigin(mock(Playable.class)).withCost(mockCost);
	}

	@Test
	public void testQueue() {
		Playable mockPlayable = mock(Playable.class);
		when(mockPlayable.getGame()).thenReturn(mock(Game.class));
		Actionable mockActionable1 = mock(Actionable.class);
		Actionable mockActionable2 = mock(Actionable.class);
		Play script1 = new Play(builder.withOrigin(mockPlayable).withActionables(Arrays.asList(() -> mockActionable1, () -> mockActionable2)));
		when(mockActionable1.getParent()).thenReturn(script1);
		when(mockActionable2.getParent()).thenReturn(script1);

		timeline.queue(script1);
		timeline.executeNext();
		timeline.executeNext();
		verify(mockActionable1).execute();
		verify(mockActionable2, never()).execute();
	}

	@Test
	public void TestQueuePayCost() {
		Actionable mockActionable1 = mock(Actionable.class);
		Cost mockCost = mock(Cost.class);
		when(mockCost.getCostActionable()).thenReturn(mock(CostActionable.class));
		Play play1 = new Play(builder.withActionables(Arrays.asList(() -> mockActionable1)));
		play1.setCost(mockCost);

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
		Play script1 = new Play(builder.withActionables(Arrays.asList(() -> mockActionable1)));
		when(mockActionable1.getParent()).thenReturn(script1);

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

		Play play1 = new Play(builder.withActionables(Arrays.asList(() -> mockActionable1)));
		when(mockActionable1.getParent()).thenReturn(play1);
		Play play2 = new Play(builder.withActionables(Arrays.asList(() -> mockActionable2, () -> mockActionable3, () -> mockActionable4)));
		when(mockActionable2.getParent()).thenReturn(play2);
		when(mockActionable3.getParent()).thenReturn(play2);
		when(mockActionable4.getParent()).thenReturn(play2);
		Play play3 = new Play(builder.withActionables(Arrays.asList(() -> mockActionable5, () -> mockActionable6)));
		when(mockActionable5.getParent()).thenReturn(play3);
		when(mockActionable6.getParent()).thenReturn(play3);

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
		verify(mockActionable1).setParent(play1);
		verify(mockActionable2).execute();
		verify(mockActionable2).getMessage();
		verify(mockActionable2).getActionableId();
		verify(mockActionable2, times(3)).getParent();
		verify(mockActionable2).rollback();
		verify(mockActionable2).setParent(play2);
		verify(mockActionable3, times(2)).getParent();
		verify(mockActionable3).setParent(play2);
		verify(mockActionable4).getParent();
		verify(mockActionable4).setParent(play2);
		verify(mockActionable5).execute();
		verify(mockActionable5).getMessage();
		verify(mockActionable5).getParent();
		verify(mockActionable5).getActionableId();
		verify(mockActionable5).setParent(play3);
		verify(mockActionable6).setParent(play3);
		verifyNoMoreInteractions(mockActionable1, mockActionable2, mockActionable3, mockActionable4, mockActionable5,
				mockActionable6);
		assertThat(timeline.getCurrent()).isEqualTo(mockActionable6);
	}

	@Test
	public void testCancelPlayCompleted() {
		Actionable mockActionable1 = mock(Actionable.class);
		builder.withActionables(Arrays.asList(() -> mockActionable1));
		Play play1 = new Play(builder);
		when(mockActionable1.getParent()).thenReturn(play1);

		timeline.queue(play1);
		timeline.executeNext();
		timeline.executeNext();
		timeline.cancelCurrentPlay();

		verify(mockActionable1).execute();
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
		Play play1 = new Play(builder.withActionables(Arrays.asList(() -> mockActionable1, () -> mockActionable2)));
		when(mockActionable1.getParent()).thenReturn(play1);
		when(mockActionable2.getParent()).thenReturn(play1);

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
		verify(mockActionable1).setParent(play1);
		verify(mockActionable2, times(2)).getParent();
		verify(mockActionable2).setParent(play1);
		verifyNoMoreInteractions(mockActionable1, mockActionable2);
		assertThat(timeline.getCurrent()).isNull();
	}

	@Test
	public void testPlayWithCost() {
		Actionable mockActionable1 = mock(Actionable.class);
		CostActionable mockCostActionable = mock(CostActionable.class);
		Cost mockCost = mock(Cost.class);
		when(mockCost.getCostActionable()).thenReturn(mockCostActionable);
		when(mockCostActionable.isSet()).thenReturn(true);
		Play play1 = new Play(builder.withCost(mockCost).withActionables(Arrays.asList(() -> mockActionable1)));

		timeline.queue(play1);
		timeline.executeNext();
		timeline.executeNext();
		verify(mockCostActionable).execute();
		verify(mockCostActionable).getMessage();
		verify(mockCostActionable).getParent();
		verify(mockCostActionable).getActionableId();
		verify(mockCostActionable).isSet();
		verify(mockCostActionable, times(2)).setParent(play1);
		verify(mockActionable1).execute();
		verify(mockActionable1).getMessage();
		verify(mockActionable1).getParent();
		verify(mockActionable1).getActionableId();
		verify(mockActionable1).setParent(play1);
		verifyNoMoreInteractions(mockCostActionable, mockActionable1);
	}

	@Test
	public void testPlayCostNotPaid() {
		Actionable mockActionable1 = mock(Actionable.class);
		CostActionable mockCostActionable = mock(CostActionable.class);
		Cost mockCost = mock(Cost.class);
		when(mockCost.getCostActionable()).thenReturn(mockCostActionable);
		Play play1 = new Play(builder.withCost(mockCost).withActionables(Arrays.asList(() -> mockActionable1)));
		play1.setCost(mockCost);
		when(mockCostActionable.getParent()).thenReturn(play1);
		when(mockActionable1.getParent()).thenReturn(play1);

		timeline.queue(play1);
		timeline.executeNext();
		verify(mockCostActionable).isSet();
		verify(mockCostActionable, times(2)).getParent();
		verify(mockCostActionable).getActionableId();
		verify(mockCostActionable, times(2)).setParent(play1);
		verify(mockActionable1).getParent();
		verify(mockActionable1).setParent(play1);
		verifyNoMoreInteractions(mockActionable1, mockCostActionable);
	}

	@Test
	public void testChoiceActionable() {
		ChoiceActionable mockActionable1 = mock(ChoiceActionable.class);
		ChoiceActionable mockActionable2 = mock(ChoiceActionable.class);
		when(mockActionable1.isSet()).thenReturn(true);
		Play play1 = new Play(builder.withActionables(Arrays.asList(() -> mockActionable1, () -> mockActionable2)));

		timeline.queue(play1);
		timeline.executeNext();
		timeline.executeNext();
		timeline.executeNext();
		verify(mockActionable1).isSet();
		verify(mockActionable1).setParent(play1);
		verify(mockActionable1).execute();
		verify(mockActionable1).getActionableId();
		verify(mockActionable1).getMessage();
		verify(mockActionable1).getParent();
		verify(mockActionable2).isSet();
		verify(mockActionable2).getActionableId();
		verify(mockActionable2).setParent(play1);
		verifyNoMoreInteractions(mockActionable1, mockActionable2);
	}

	@Test
	public void testGameTriggersOrder() {
		Actionable mockActionable1 = mock(Actionable.class);
		Actionable mockActionable2 = mock(Actionable.class);
		Play play1 = new Play(builder.withActionables(Arrays.asList(() -> mockActionable1,() -> mockActionable2)));

		timeline.queue(play1);
		timeline.executeNext();
		verify(game).getAfterTriggerActionables(any(CostActionable.class));
		verify(game).getBeforeTriggerActionables(mockActionable1);
		timeline.executeNext();
		verify(game).getAfterTriggerActionables(mockActionable1);
		verify(game).getBeforeTriggerActionables(mockActionable2);
		timeline.executeNext();
		verify(game).getAfterTriggerActionables(mockActionable2);
		timeline.executeNext();
		verifyNoMoreInteractions(game);
	}
}
