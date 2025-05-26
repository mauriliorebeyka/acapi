package com.rebeyka.acapi.actionables;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.Types;
import com.rebeyka.acapi.entities.gameflow.Play;

public class ChangeAttributeActionableTest {

	@Test
	public void testFunction() {
		Attribute<Integer> attribute = new Attribute<Integer>("", 0, Types.integer());
		ChangeAttributeActionable<Integer> actionable = new ChangeAttributeActionable<Integer>("", attribute,
				a -> a + 3);
		actionable.execute();
		assertThat(attribute.getValue()).isEqualTo(3);
		actionable.rollback();
		assertThat(attribute.getValue()).isEqualTo(0);
	}

	@Test
	public void testValue() {
		Attribute<Integer> attribute = new Attribute<Integer>("ATTR", 10, Types.integer());
		ChangeAttributeActionable<Integer> actionable = new ChangeAttributeActionable<Integer>("", attribute, 33);
		Play play = mock(Play.class);
		actionable.setParent(play);
		Playable playable = mock(Playable.class);
		when(playable.toString()).thenReturn("DUMMY PLAYABLE");
		when(play.getOrigin()).thenReturn(playable);
		actionable.execute();
		assertThat(attribute.getValue()).isEqualTo(33);
		assertThat(actionable.getMessage()).isEqualTo("Changing attribute ATTR on DUMMY PLAYABLE to 33");
	}

	@Test
	public void testBiFunction() {
		Attribute<Integer> attribute = new Attribute<Integer>("", 10, Types.integer());
		Attribute<Integer> attribute2 = new Attribute<Integer>("", 2, Types.integer());
		ChangeAttributeActionable<Integer> actionable = new ChangeAttributeActionable<Integer>("", attribute,
				attribute2.getValue(), Math::subtractExact);
		actionable.execute();
		assertThat(attribute.getValue()).isEqualTo(8);
	}
}
