package com.rebeyka.acapi.entities.cost;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.SimpleIntegerAttribute;
import com.rebeyka.entitities.cost.PlayableSequenceCost;

public class CardGroupCostTest {

	@Test
	public void testCostActionable() {
		Playable mockPlayable1 = mock(Playable.class);
		doReturn(new SimpleIntegerAttribute(7)).when(mockPlayable1).getAttribute("number");
		Playable mockPlayable2 = mock(Playable.class);
		doReturn(new SimpleIntegerAttribute(8)).when(mockPlayable2).getAttribute("number");
		PlayableSequenceCost cost = new PlayableSequenceCost("number",List.of(mockPlayable1,mockPlayable2));
		assertThat(cost.isPaid());
	}
	
	@Test
	public void testNot() {
		Playable mockPlayable1 = mock(Playable.class);
		doReturn(new SimpleIntegerAttribute(1)).when(mockPlayable1).getAttribute("number");
		Playable mockPlayable2 = mock(Playable.class);
		doReturn(new SimpleIntegerAttribute(3)).when(mockPlayable2).getAttribute("number");
		PlayableSequenceCost cost = new PlayableSequenceCost("number",List.of(mockPlayable1,mockPlayable2));
		assertThat(!cost.isPaid());
	}
	
	@Test
	public void testWrong() {
		Playable mockPlayable1 = mock(Playable.class);
		doReturn(new SimpleIntegerAttribute(1)).when(mockPlayable1).getAttribute("number");
		Playable mockPlayable2 = mock(Playable.class);
		doReturn(new Attribute<String>("2")).when(mockPlayable2).getAttribute("number");
		Playable mockPlayable2right = mock(Playable.class);
		doReturn(new SimpleIntegerAttribute(2)).when(mockPlayable2right).getAttribute("number");
		PlayableSequenceCost cost = new PlayableSequenceCost("number",List.of(mockPlayable1,mockPlayable2,mockPlayable2right));
		assertThat(!cost.isPaid());
	}
}
