package com.rebeyka.acapi.random;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DiceSetTest {

	@Test
	public void testDiceSet() {
		DiceSet<String> set = new DieBuilder<String>().withFaces("YES").addFace("NO").addFace("MAYBE", 2)
				.withRandomSeed().buildDice(1000);
		set.rollAll();
		assertThat(set.getValues()).hasSize(1000).containsOnly("YES", "NO", "MAYBE");
		assertThat(set.getOcurrenceMap().get("MAYBE")).isBetween(400, 600);
	}

	@Test
	public void testDiceSetSum() {
		DiceSet<Integer> set = new DieBuilder<Integer>().withRandomSeed().withFaces(1).buildDice(1000);
		set.rerollAll();
		assertThat(set.getSum()).isEqualTo(1000);
		assertThat(set.getSum(i -> i == 2)).isEqualTo(0);
	}
}
