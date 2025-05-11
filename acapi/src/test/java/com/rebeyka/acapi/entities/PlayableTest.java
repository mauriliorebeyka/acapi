package com.rebeyka.acapi.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import com.google.common.reflect.TypeToken;
import com.rebeyka.acapi.exceptions.InvalidAttributeTypeException;
import com.rebeyka.acapi.random.DiceSet;
import com.rebeyka.acapi.random.DieBuilder;

public class PlayableTest {

	@Test
	public void testGetAttribute() {
		Player player = new Player("player");
		player.getAttribute("string", Types.string()).setValue("String value");
		player.getAttribute("int",Types.integer()).setValue(5);
		Attribute<String> expectedString = player.getAttribute("string", Types.string());
		Attribute<Integer> expectedInt = player.getAttribute("int", Types.integer());
		assertThat(expectedString.get()).isEqualTo("String value");
		assertThat(expectedInt.getValue()).isEqualTo(5);
	}

	@Test
	public void testAttributeSameInstance() {
		Player player = new Player("test");
		Attribute<String> preInitialize = player.getAttribute("test", Types.string());
		player.getAttribute("test", Types.string()).setValue("value");
		Attribute<String> attribute = player.getAttribute("test", Types.string());
		attribute.setValue("other test");
		assertThat(attribute).isSameAs(preInitialize);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testChangeAttributeType() {
		Player player = new Player("");
		Attribute attr = player.getAttribute("test", Types.string());
		player.getAttribute("test", Types.string()).setValue("test");
		assertThatThrownBy(() -> player.getAttribute("test", Types.integer()))
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
		Attribute<DiceSet<Integer>> dice = player.getAttribute("attr", new TypeToken<DiceSet<Integer>>() {
			private static final long serialVersionUID = 3170576435674745924L;});
		dice.setValue(DieBuilder.buildBasicDiceSet(1, 2));
		dice.getValue().rollAll();
		assertThat(dice).isSameAs(player.getAttribute("attr", Types.diceSetOf(dice.getValue())));
		assertThat(dice.getValue()).isInstanceOf(DiceSet.class);
	}

}