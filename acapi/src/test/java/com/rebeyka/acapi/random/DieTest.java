package com.rebeyka.acapi.random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.exceptions.DieAlreadyRolledException;
import com.rebeyka.acapi.exceptions.DieNotRolledException;

public class DieTest {

	@Test
	public void testRoll() {
		Die<Integer> die = new DieBuilder<Integer>().withRandomSeed().withFaces(1).build();
		die.roll();
		int value = die.getIntValue();
		int a = die.getValue();
		assertThat(value).isEqualTo(a).isEqualTo(1);
		assertThat(die.isRolled()).isTrue();
		assertThatThrownBy(() -> die.roll()).isInstanceOf(DieAlreadyRolledException.class);
	}

	@Test
	public void testMultipleRolls() {
		Die<Integer> die = DieBuilder.buildBasicDie(4);
		Map<Integer, Integer> rolledValues = new HashMap<>();
		rolledValues.put(1, 0);
		rolledValues.put(2, 0);
		rolledValues.put(3, 0);
		rolledValues.put(4, 0);
		for (int i = 0; i < 1000; i++) {
			die.reroll();
			rolledValues.put(die.getValue(), rolledValues.get(die.getValue()) + 1);
		}
		assertThat(rolledValues).allSatisfy((key, value) -> assertThat(value).isBetween(200, 300));
	}

	@Test
	public void testWeightedRoll() {
		Die<Integer> die = new DieBuilder<Integer>().withSeed(new Random(0)).withFaces(4).withWeightedValue(1, 3)
				.build();
		int ones = 0;
		for (int i = 0; i < 1000; i++) {
			if (die.reroll().getIntValue() == 1) {
				ones++;
			}
		}
		assertThat(ones).isBetween(400, 600);
	}

	@Test
	public void testReset() {
		Die<Integer> die = DieBuilder.buildBasicDie(6);
		die.roll().reset();
		assertThat(die.isRolled()).isFalse();
		assertThatThrownBy(() -> die.getValue()).isInstanceOf(DieNotRolledException.class);
		assertThatThrownBy(() -> die.getIntValue()).isInstanceOf(DieNotRolledException.class);
	}

	@Test
	public void testInvalidBuilds() {
		DieBuilder<String> builder = new DieBuilder<String>();
		assertThatThrownBy(() -> builder.build()).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Seed not provided");
		builder.withRandomSeed();
		assertThatThrownBy(() -> builder.build()).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Die has no defined faces");
		builder.addFace("face");
		assertThat(builder.build().roll().getValue()).isEqualTo("face");
		builder.withFaces(new ArrayList<>());
		assertThatThrownBy(() -> builder.build()).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Die has no defined faces");
	}
}
