package com.rebeyka.acapi.check;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.actionables.Actionable;

public class AbstractCheckTest {

	@Test
	public void testDifferentInstances() {
		ActionableCheck<Actionable, ?> root = Checker.whenActionable();
		ActionableCheck<Actionable, ?> always = (ActionableCheck<Actionable, ?>) root.always();
		System.out.println(always);
		boolean result = always.not().always().check(mock(Actionable.class));
		System.out.println("assertions");
//		assertThat(Checker.whenActionable().not().always().check(null)).isFalse();
		assertThat(Checker.whenString().always().always().always().always().check("")).isTrue();
		assertThat(result).isFalse();
		assertThat(root.check(null)).isFalse();
//		assertThat(always.check(null)).isTrue();
	}
}
