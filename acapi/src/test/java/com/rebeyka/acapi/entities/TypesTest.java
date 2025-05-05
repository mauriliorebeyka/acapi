package com.rebeyka.acapi.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.google.common.reflect.TypeToken;
import com.rebeyka.acapi.random.DiceSet;

public class TypesTest {

	@Test
	public void testTypes() {
		assertThat(Types.integer().getType()).isEqualTo(Integer.class);
		assertThat(Types.of(Integer.class)).isEqualTo(Types.integer());
		TypeToken<DiceSet<Integer>> token = Types.diceSetOf(Types.integer());
		assertThat(token.getType().getTypeName()).isEqualTo("com.rebeyka.acapi.random.DiceSet<java.lang.Integer>");
		assertThat(Types.diceSetOf(String.class)).isEqualTo(Types.diceSetOf(Types.string()));
	}
	
}
