package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.check.Checker;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.entities.gameflow.Trigger;

public class TriggerTest {

	@Test
	public void testTriggeredPlay() {
		Actionable mockActionable = mock(Actionable.class);
		Play play = new Play.Builder().name("play").actionable(() -> mockActionable).build();
		when(mockActionable.getParent()).thenReturn(play);
		Trigger trigger = new Trigger(play);
		assertThat(trigger.test(mockActionable)).isTrue();
		Play triggeredPlay = trigger.getTriggeredPlay(play);
		when(mockActionable.getParent()).thenReturn(triggeredPlay);
		assertThat(trigger.test(triggeredPlay.getActionables().get(0))).isFalse();
		assertThat(play).isNotEqualTo(triggeredPlay);
	}
	
	@Test
	public void testTriggerByActionableId() {
		Trigger trigger = new Trigger(mock(Play.class), "RIGHT");
		
		Actionable rightActionable = mock(Actionable.class);
		when(rightActionable.getActionableId()).thenReturn("RIGHT");
		when(rightActionable.getParent()).thenReturn(mock(Play.class));
		Actionable wrongActionable = mock(Actionable.class);
		when(wrongActionable.getActionableId()).thenReturn("WRONG");
		when(wrongActionable.getParent()).thenReturn(mock(Play.class));
		
		assertThat(trigger.test(rightActionable)).isTrue();
		assertThat(trigger.test(wrongActionable)).isFalse();
	}
	
	@Test
	public void testTriggerByCondition() {
		Actionable mockActionable = mock(Actionable.class);
		when(mockActionable.getParent()).thenReturn(mock(Play.class));
		Actionable wrongActionable = mock(Actionable.class);
		when(wrongActionable.getParent()).thenReturn(mock(Play.class));
		Trigger trigger = new Trigger(Checker.whenActionable().is(mockActionable),mock(Play.class),"ALL");
		
		assertThat(trigger.test(mockActionable)).isTrue();
		assertThat(trigger.test(wrongActionable)).isFalse();
	}
}
