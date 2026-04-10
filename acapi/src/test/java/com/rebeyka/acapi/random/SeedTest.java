package com.rebeyka.acapi.random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.offset;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class SeedTest {

	@Test
	public void testBack() {
		Seed seed = new Seed();
		double d = seed.nextDouble();
		seed.rollback();
		assertThat(seed.nextDouble()).isEqualTo(d);
	}
	
	@Test
	public void testInt() {
		Seed seed = new Seed();
		Set<Integer> usedNumbers = new HashSet<>();
		for (int i=0; i<1000; i++) {
			int number = seed.nextInt(3);
			usedNumbers.add(number);
			assertThat(number).isBetween(0,3);
		}
		assertThat(usedNumbers).containsOnly(0,1,2);
	}
	
	@Test
	public void testOneInXFail() {
		Seed seed = new Seed();
		assertThatThrownBy(() -> seed.oneIn(0)).isInstanceOf(IllegalArgumentException.class).hasMessage("Chance must be greater than 0");
	}
	
	@Test
	public void testXInY100PercentChance() {
		Seed seed = new Seed();
		for (int i=0; i<100000; i++) { 
			assertThat(seed.xInY(10,10)).isEqualTo(true);
		}
	}
	
	@Test
	public void testOneInChanceProbability() {
		Seed seed = new Seed();
        int trials = 100000;
        int hits = 0;

        double successes = 2.0; // 50%

        for (int i = 0; i < trials; i++) {
            if (seed.oneIn(successes)) {
                hits++;
            }
        }

        double observed = hits / (double) trials;
        double expected = 1 / successes;

        assertThat(observed).isCloseTo(expected, offset(0.05));

	}
}
