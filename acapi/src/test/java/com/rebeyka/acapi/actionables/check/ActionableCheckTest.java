package com.rebeyka.acapi.actionables.check;

import static com.rebeyka.acapi.actionables.check.Checker.whenActionable;
import static com.rebeyka.acapi.actionables.check.Checker.whenPlayable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
		ActionableChecker a = Checker.whenActionable();
		a.id().is("ID").origin().id().is("PLAYER ID");
		Actionable actionable = mock(Actionable.class);
		Play play = mock(Play.class);
		Player player = mock(Player.class);
		Attribute<String> attribute = mock(Attribute.class);
		SimpleIntegerAttribute intAttribute = new SimpleIntegerAttribute(10);
		when(actionable.getActionableId()).thenReturn("ID");
		when(actionable.getParent()).thenReturn(play);
		when(actionable.check(any())).thenCallRealMethod();
		when(play.getOrigin()).thenReturn(player);
		doReturn(attribute).when(player).getAttribute("HP");
		when(player.getId()).thenReturn("PLAYER ID");
		doReturn(intAttribute).when(player).getAttribute("int");
		when(attribute.get()).thenReturn("55");
		assertThat(whenActionable().id().is("ID").origin().attributeAsInt("int").biggerThan(5).attribute("HP")
				.is("55").attribute("HP").contains("5").isPlayer().check(actionable)).isTrue();
		assertThat(whenPlayable().id().is("PLAYER ID").check(player)).isTrue();
		assertThat(a.check(actionable)).isTrue();
		assertThat(actionable.check(whenActionable().id().is("ID"))).isTrue();
	}
}
