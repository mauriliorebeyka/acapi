package com.rebeyka.acapi.actionables;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rebeyka.acapi.entities.Cost;
import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.gameflow.Play;

public class SimpleCostActionableTest {

	@Mock
	private Cost mockCost;

	@Mock
	private Play mockPlay;

	@Mock
	private Game mockGame;

	@Mock
	private Actionable mockActionable;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		when(mockPlay.getGame()).thenReturn(mockGame);
		when(mockGame.getSelectedChoices()).thenReturn(Collections.emptyList());
		when(mockCost.isPaid(Collections.emptyList())).thenReturn(true);
		when(mockActionable.supply()).thenReturn(() -> mockActionable);
	}

	@Test
	public void testIsSet() {
		SimpleCostActionable actionable = new SimpleCostActionable("test", mockCost, mockActionable);
		actionable.setParent(mockPlay);
		assertThat(actionable.getActionableId()).isEqualTo("test");
		assertThat(actionable.isSet()).isTrue();
	}

	@Test
	public void testExecute() {
		Playable mockPlayable1 = mock(Playable.class);
		when(mockPlayable1.toString()).thenReturn("mockPlayable1");
		Playable mockPlayable2 = mock(Playable.class);
		when(mockPlayable2.toString()).thenReturn("mockPlayable2");
		when(mockGame.getSelectedChoices()).thenReturn(List.of(mockPlayable1, mockPlayable2));
		SimpleCostActionable actionable = new SimpleCostActionable("test", mockCost, mockActionable);
		actionable.setParent(mockPlay);
		actionable.execute();
		verify(mockGame).setSelectedChoices(Collections.emptyList());
		assertThat(actionable.getCostPlays()).hasSize(2).extracting(Play::getTargets)
				.containsExactly(List.of(mockPlayable2), List.of(mockPlayable1));
		assertThat(actionable.getCostPlays().get(0).getActionableTemplates()).containsExactly(mockActionable);
		assertThat(actionable.getMessage()).isEqualTo("Paying cost mockCost with playables [mockPlayable1, mockPlayable2]");
	}
	
}
