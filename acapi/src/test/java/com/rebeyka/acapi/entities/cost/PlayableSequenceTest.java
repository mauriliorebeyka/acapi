package com.rebeyka.acapi.entities.cost;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.Types;

public class PlayableSequenceTest {

	@Test
	public void testPaid() {
		Playable mockPlayable1 = mock(Playable.class);
		doReturn(new Attribute<Integer>("",7,Types.integer())).when(mockPlayable1).getAttribute("number",Types.integer());
		Playable mockPlayable2 = mock(Playable.class);
		doReturn(new Attribute<Integer>("",8,Types.integer())).when(mockPlayable2).getAttribute("number",Types.integer());
		PlayableSequenceCost cost = new PlayableSequenceCost("number");
		assertThat(cost.isPaid(List.of(mockPlayable1,mockPlayable2))).isTrue();
	}
	
	@Test
	public void testNot() {
		Playable mockPlayable1 = mock(Playable.class);
		doReturn(new Attribute<Integer>("",1,Types.integer())).when(mockPlayable1).getAttribute("number",Types.integer());
		Playable mockPlayable2 = mock(Playable.class);
		doReturn(new Attribute<Integer>("",3,Types.integer())).when(mockPlayable2).getAttribute("number",Types.integer());
		PlayableSequenceCost cost = new PlayableSequenceCost("number");
		assertThat(cost.isPaid(List.of(mockPlayable1,mockPlayable2))).isFalse();
	}
	
	@Test
	public void testWrong() {
		Playable mockPlayable1 = mock(Playable.class);
		doReturn(new Attribute<Integer>("",1,Types.integer())).when(mockPlayable1).getAttribute("number",Types.integer());
		Playable mockPlayable2 = mock(Playable.class);
		doReturn(new Attribute<String>("2", Types.string())).when(mockPlayable2).getAttribute("number",Types.integer());
		Playable mockPlayable2right = mock(Playable.class);
		doReturn(new Attribute<Integer>("",2,Types.integer())).when(mockPlayable2right).getAttribute("number",Types.integer());
		PlayableSequenceCost cost = new PlayableSequenceCost("number");
		assertThat(cost.isPaid(List.of(mockPlayable1,mockPlayable2,mockPlayable2right))).isFalse();
	}
	
	@Test
	public void testMissingAttribute() {
		PlayableSequenceCost cost = new PlayableSequenceCost("");
		assertThat(cost.isPaid(List.of(new Player("")))).isFalse();
	}
	
	@Test
	public void testEmptyList() {
		assertThat(new PlayableSequenceCost("").isPaid(Collections.emptyList())).isFalse();
	}
}
