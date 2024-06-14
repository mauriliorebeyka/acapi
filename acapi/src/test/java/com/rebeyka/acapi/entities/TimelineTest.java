package com.rebeyka.acapi.entities;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class TimelineTest {

	@Test
	public void testQueue() {
		Actionable mockActionable1 = mock(Actionable.class);
		Actionable mockActionable2 = mock(Actionable.class);
		Script script1 = new Script(null, null, Arrays.asList(mockActionable1, mockActionable2));
		Actionable mockActionable3 = mock(Actionable.class);
		Script script2 = new Script(null, null, Arrays.asList(mockActionable3));

		Timeline timeline = new Timeline();
		timeline.queue(script1);
		timeline.executeNext();
		verify(mockActionable1).execute();
		timeline.queueNext(script2);
		timeline.executeNext();
		verify(mockActionable3).execute();
		verify(mockActionable2, never()).execute();
	}

	@Test
	public void testTimelineLimits() {
		Timeline timeline = new Timeline();
		assertThatNoException().isThrownBy(() -> timeline.executeNext());
		assertThatNoException().isThrownBy(() -> timeline.rollbackLast());
	}

	@Test
	public void testRollback() {
		Actionable mockActionable1 = mock(Actionable.class);
		Actionable mockActionable2 = mock(Actionable.class);
		Script script1 = new Script(null, null, Arrays.asList(mockActionable1, mockActionable2));

		Timeline timeline = new Timeline();
		timeline.queue(script1);
		timeline.executeNext();
		timeline.rollbackLast();
		timeline.executeNext();
	}
}
