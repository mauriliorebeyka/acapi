package com.rebeyka.acapi.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PlayableTest {

	@Test
	public void testGetAttribute() {
		Player player = new Player("player");
		player.setAttribute("string", new Attribute<String>("String value"));
		player.setAttribute("int", new SimpleIntegerAttribute(5));
		Attribute<String> expectedString = player.getAttribute("string", String.class);
		Attribute<Integer> expectedInt = player.getAttribute("int", Integer.class);
		SimpleIntegerAttribute expectedIntCast = (SimpleIntegerAttribute) player.getAttribute("int");
		assertThat(expectedString.get()).isEqualTo("String value");
		assertThat(expectedInt).isEqualTo(expectedIntCast);
		assertThat(expectedIntCast.getValue()).isEqualTo(5);
		
	}
}
