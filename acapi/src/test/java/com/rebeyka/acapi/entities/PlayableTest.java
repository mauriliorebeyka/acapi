package com.rebeyka.acapi.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.reflect.TypeToken;
import com.rebeyka.acapi.exceptions.InvalidAttributeTypeException;
import com.rebeyka.acapi.random.DiceSet;
import com.rebeyka.acapi.random.DieBuilder;

public class PlayableTest {

	@Mock
	private Game game;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetAttribute() {
		Player player = new Player("player");
		player.getRawAttribute("string", Types.string()).setValue("String value");
		player.setAttribute("int", Types.integer(), 5);
		player.setGame(game);
		Attribute<String> expectedString = player.getRawAttribute("string", Types.string());
		Attribute<Integer> expectedInt = player.getRawAttribute("int", Types.integer());
		when(game.getModifiedAttribute(player, expectedString)).thenReturn(expectedString);
		when(game.getModifiedAttribute(player, expectedInt)).thenReturn(expectedInt);
		assertThat(expectedString.get()).isEqualTo("String value");
		assertThat(expectedInt.getValue()).isEqualTo(5);
	}

	@Test
	public void testRawAttributeSameInstance() {
		Player player = new Player("test");
		Attribute<String> preInitialize = player.getRawAttribute("test", Types.string());
		player.getRawAttribute("test", Types.string()).setValue("value");
		player.setGame(game);
		Attribute<String> attribute = player.getRawAttribute("test", Types.string());
		attribute.setValue("other test");
		assertThat(attribute).isSameAs(preInitialize);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testChangeAttributeType() {
		Player player = new Player("");
		player.setGame(game);
		Attribute attr = player.getRawAttribute("test", Types.string());
		player.getRawAttribute("test", Types.string()).setValue("test");
		assertThatThrownBy(() -> player.getRawAttribute("test", Types.integer()))
				.isExactlyInstanceOf(InvalidAttributeTypeException.class)
				.hasMessage("Expected attribute type to be class java.lang.Integer, but was class java.lang.String instead");
		assertThat(attr.getValue()).isEqualTo("test");
		//TODO Should we dynamically change the type of the attribute or throw an exception?
		//Allowing the below behaviour will fail to fetch the attribute later.
//		((Attribute) player.getAttribute("test")).setValue(5);
//		assertThat(attr.getValue()).isEqualTo(5);
//		assertThat(attr).isSameAs(player.getAttribute("test", Types.integer()));
	}

	@Test
	public void testGenericTypedAttribute() {
		Player player = new Player("");
		player.setGame(game);
		Attribute<DiceSet<Integer>> dice = player.getRawAttribute("attr", new TypeToken<DiceSet<Integer>>() {
			private static final long serialVersionUID = 3170576435674745924L;});
		dice.setValue(DieBuilder.buildBasicDiceSet(1, 2));
		dice.getValue().rollAll();
		assertThat(dice).isSameAs(player.getRawAttribute("attr", Types.diceSetOf(dice.getValue())));
		assertThat(dice.getValue()).isInstanceOf(DiceSet.class);
	}

}