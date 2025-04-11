package com.rebeyka.acapi.actionables.check;

import static com.rebeyka.acapi.actionables.check.ActionableChecker.whenActionable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.SimpleIntegerAttribute;

public class ActionableCheckTest {

	@Test
	public void test() {
		ActionableChecker a = ActionableChecker.whenActionable();
		a.origin().isPlayer().id().equalsTo("ID");
		Actionable actionable = mock(Actionable.class);
		Play play = mock(Play.class);
		Player player = mock(Player.class);
		Attribute attribute = mock(Attribute.class);
		SimpleIntegerAttribute intAttribute = new SimpleIntegerAttribute(10);
		when(actionable.getActionableId()).thenReturn("ID");
		when(actionable.getParent()).thenReturn(play);
		when(play.getOrigin()).thenReturn(player);
		when(player.getAttribute("HP")).thenReturn(attribute);
		doReturn(intAttribute).when(player).getAttribute("int");
		when(attribute.get()).thenReturn("55");
		assertThat(whenActionable().id().equalsTo("ID").origin().attributeAsInt("int").biggerThan(5).attribute("HP")
				.equalsTo("55").attribute("HP").contains("5").isPlayer().check(actionable)).isTrue();
		assertThat(a.check(actionable)).isTrue();
	}
}
