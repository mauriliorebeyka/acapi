package com.rebeyka.acapi.entities.gameflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.check.Checker;
import com.rebeyka.acapi.check.PlayableCheck;
import com.rebeyka.acapi.entities.Playable;

public class TriggerTest {

	@Mock
	private Play play;
	
	@Mock
	private Playable playable;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		when(play.getOrigin()).thenReturn(playable);
		when(play.getCondition()).thenReturn(Checker.whenPlayable().always());
		when(playable.getId()).thenReturn("PLAYABLE_ID");
	}
	
	@Test
	public void testTriggeredPlay() {
		Actionable mockActionable = mock(Actionable.class);
		when(play.getActionables()).thenReturn(List.of(mockActionable));
		when(mockActionable.getParent()).thenReturn(play);
		Trigger trigger = new Trigger(play);
		assertThat(trigger.test(mockActionable)).isTrue();
		Play triggeredPlay = trigger.getTriggeredPlay();
		when(mockActionable.getParent()).thenReturn(triggeredPlay);
		when(mockActionable.copy(play)).thenReturn(mockActionable);
		when(triggeredPlay.getTriggeredBy()).thenReturn(trigger);
		assertThat(trigger.test(triggeredPlay.getActionables().get(0))).isFalse();
		assertThat(play).isSameAs(triggeredPlay);
	}
	
	@Test
	public void testTriggerByActionableId() {
		Trigger trigger = new Trigger(play, "RIGHT");
		
		Actionable rightActionable = mock(Actionable.class);
		when(rightActionable.getActionableId()).thenReturn("RIGHT");
		when(rightActionable.getParent()).thenReturn(play);
		Actionable wrongActionable = mock(Actionable.class);
		when(wrongActionable.getActionableId()).thenReturn("WRONG");
		when(wrongActionable.getParent()).thenReturn(play);
		
		assertThat(trigger.test(rightActionable)).isTrue();
		assertThat(trigger.test(wrongActionable)).isFalse();
	}
	
	@Test
	public void testTriggerByCondition() {
		Actionable mockActionable = mock(Actionable.class);
		when(mockActionable.getParent()).thenReturn(play);
		Actionable wrongActionable = mock(Actionable.class);
		when(wrongActionable.getParent()).thenReturn(play);
		Trigger trigger = new Trigger(Checker.whenActionable().is(mockActionable),play,"ALL");
		
		assertThat(trigger.test(mockActionable)).isTrue();
		assertThat(trigger.test(wrongActionable)).isFalse();
	}
}
