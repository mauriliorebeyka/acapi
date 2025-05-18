package com.rebeyka.acapi.check;

import static com.rebeyka.acapi.check.Checker.whenActionable;
import static com.rebeyka.acapi.check.Checker.whenPlayable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Play;
import com.rebeyka.acapi.entities.Player;
import com.rebeyka.acapi.entities.Types;

public class ActionableCheckTest {
	
	@Test
	public void test() {
		ActionableCheck<Actionable> a = Checker.whenActionable();
		a.id().sameValue("ID").origin().id().sameValue("PLAYER ID");
		Actionable actionable = mock(Actionable.class);
		Play play = mock(Play.class);
		Player player = mock(Player.class);
		Attribute<String> attribute = new Attribute<String>("", "55", Types.string());
		Attribute<Integer> intAttribute = new Attribute<Integer>("", 10, Types.integer());
		when(actionable.getActionableId()).thenReturn("ID");
		when(actionable.getParent()).thenReturn(play);
		when(actionable.check(any())).thenCallRealMethod();
		when(play.getOrigin()).thenReturn(player);
		when(player.getAttribute("HP", Types.string())).thenReturn(attribute);
		when(player.getId()).thenReturn("PLAYER ID");
		when(player.getAttribute("int", Types.integer())).thenReturn((Attribute<Integer>)intAttribute);
		assertThat(whenActionable().id().sameValue("ID").not().origin().attributeAsInt("int").biggerThan(5).attribute("HP")
				.sameValue("55").attribute("HP").is("55").contains("5").isPlayer().check(actionable)).isTrue();
		assertThat(whenPlayable().id().sameValue("PLAYER ID").check(player)).isTrue();
		assertThat(a.check(actionable)).isTrue();
		assertThat(actionable.check(whenActionable().id().is("ID"))).isTrue();
	}
	
	@Test
	public void testAbstractChecks() {
		Actionable actionable = mock(Actionable.class);
		assertThat(whenActionable().custom(_ -> true).check(actionable)).isTrue();
	}
}
