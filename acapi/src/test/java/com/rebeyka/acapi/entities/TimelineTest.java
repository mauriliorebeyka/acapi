package com.rebeyka.acapi.entities;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class TimelineTest {

	private Timeline timeline;

	@Mock
	private Game game;

	@BeforeEach
	public void before() {
		openMocks(this);
		timeline = new Timeline(game);
	}

	@Test
	public void testQueue() {
		Actionable mockActionable1 = mock(Actionable.class);
		Actionable mockActionable2 = mock(Actionable.class);
		Play script1 = new Play(null, null, Arrays.asList(mockActionable1, mockActionable2));
		Actionable mockActionable3 = mock(Actionable.class);
		Play script2 = new Play(null, null, Arrays.asList(mockActionable3));

		timeline.queue(script1);
		timeline.executeNext();
		verify(mockActionable1).execute();
		timeline.queueNext(script2);
		timeline.executeNext();
		verify(mockActionable3).execute();
		verify(mockActionable2, never()).execute();
	}

	@Test
	public void TestQueuePayCost() {
		Actionable mockActionable1 = mock(Actionable.class);
		Cost mockCost = mock(Cost.class);
		Play play1 = new Play(null, null, Arrays.asList(mockActionable1));
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
		Actionable mockActionable2 = mock(Actionable.class);
		Play script1 = new Play(null, null, Arrays.asList(mockActionable1, mockActionable2));

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

		Play play1 = new Play(null, null, Arrays.asList(mockActionable1));
		when(mockActionable1.getParent()).thenReturn(play1);
		Play play2 = new Play(null, null, Arrays.asList(mockActionable2, mockActionable3, mockActionable4));
		when(mockActionable2.getParent()).thenReturn(play2);
		when(mockActionable3.getParent()).thenReturn(play2);
		when(mockActionable4.getParent()).thenReturn(play2);
		Play play3 = new Play(null, null, Arrays.asList(mockActionable5, mockActionable6));
		when(mockActionable5.getParent()).thenReturn(play3);
		when(mockActionable6.getParent()).thenReturn(play3);

		timeline.queue(play1);
		timeline.queue(play2);
		timeline.queue(play3);

		timeline.executeNext();
		timeline.executeNext();
		timeline.cancelCurrentPlay();
		timeline.executeNext();

		verify(mockActionable1).execute();
		verify(mockActionable1).getParent();
		verify(mockActionable2).execute();
		verify(mockActionable2, times(2)).getParent();
		verify(mockActionable2).rollback();
		verify(mockActionable3, times(2)).getParent();
		verify(mockActionable4).getParent();
		verify(mockActionable5).execute();
		verify(mockActionable5).getParent();
		verifyNoMoreInteractions(mockActionable1, mockActionable2, mockActionable3, mockActionable4, mockActionable5,
				mockActionable6);
	}

	@Test
	public void testCancelPlayCompleted() {
		Actionable mockActionable1 = mock(Actionable.class);
		Play play1 = new Play(null, null, Arrays.asList(mockActionable1));
		when(mockActionable1.getParent()).thenReturn(play1);

		timeline.queue(play1);
		timeline.executeNext();
		timeline.cancelCurrentPlay();

		verify(mockActionable1).execute();
		verifyNoMoreInteractions(mockActionable1);
	}

	@Test
	public void testCancelPlayNoFurtherActionable() {
		Actionable mockActionable1 = mock(Actionable.class);
		Actionable mockActionable2 = mock(Actionable.class);
		Play play1 = new Play(null, null, Arrays.asList(mockActionable1, mockActionable2));
		when(mockActionable1.getParent()).thenReturn(play1);
		when(mockActionable2.getParent()).thenReturn(play1);

		timeline.queue(play1);
		timeline.executeNext();
		timeline.cancelCurrentPlay();
		timeline.executeNext();

		verify(mockActionable1).execute();
		verify(mockActionable1).rollback();
		verify(mockActionable1, times(2)).getParent();
		verify(mockActionable2, times(2)).getParent();
		verifyNoMoreInteractions(mockActionable1, mockActionable2);
	}

	@Test
	public void testPlayWithCost() {
		Actionable mockActionable1 = mock(Actionable.class);
		CostActionable mockCostActionable = mock(CostActionable.class);
		Cost mockCost = mock(Cost.class);
		when(mockCost.generateActionable()).thenReturn(mockCostActionable);
		when(mockCostActionable.isSet()).thenReturn(true);
		Play play1 = new Play(null, null, Arrays.asList(mockActionable1));
		play1.setCost(mockCost);

		timeline.queue(play1);
		timeline.executeNext();
		timeline.executeNext();
		verify(mockCostActionable).execute();
		verify(mockCostActionable).isSet();
		verify(mockActionable1).execute();
		verifyNoMoreInteractions(mockCostActionable, mockActionable1);
	}

	@Test
	public void testPlayCostNotPaid() {
		Actionable mockActionable1 = mock(Actionable.class);
		CostActionable mockCostActionable = mock(CostActionable.class);
		Cost mockCost = mock(Cost.class);
		when(mockCost.generateActionable()).thenReturn(mockCostActionable);
		Play play1 = new Play(null, null, Arrays.asList(mockActionable1));
		play1.setCost(mockCost);
		when(mockCostActionable.getParent()).thenReturn(play1);
		when(mockActionable1.getParent()).thenReturn(play1);

		timeline.queue(play1);
		timeline.executeNext();
		verify(mockCostActionable).isSet();
		verify(mockCostActionable, times(2)).getParent();
		verify(mockActionable1).getParent();
		verifyNoMoreInteractions(mockActionable1, mockCostActionable);
	}

	@Test
	public void testChoiceActionable() {
		ChoiceActionable mockActionable1 = mock(ChoiceActionable.class);
		ChoiceActionable mockActionable2 = mock(ChoiceActionable.class);
		when(mockActionable1.isSet()).thenReturn(true);
		Play play1 = new Play(null, null, Arrays.asList(mockActionable1, mockActionable2));

		timeline.queue(play1);
		timeline.executeNext();
		timeline.executeNext();
		verify(mockActionable1).isSet();
		verify(mockActionable2).isSet();
		verify(mockActionable1).execute();
		verifyNoMoreInteractions(mockActionable1, mockActionable2);
	}

	@Test
	public void testGameTriggersOrder() {
		Actionable mockActionable1 = mock(Actionable.class);
		Actionable mockActionable2 = mock(Actionable.class);
		Play play1 = new Play(null, null, Arrays.asList(mockActionable1, mockActionable2));

		timeline.queue(play1);
		timeline.executeNext();
		verify(game).getTriggeredActionables(mockActionable1);
		verify(game).getTriggeringActionables(mockActionable2);
		verifyNoMoreInteractions(game);
	}
}
