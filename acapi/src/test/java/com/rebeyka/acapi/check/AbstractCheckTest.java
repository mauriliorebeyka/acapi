package com.rebeyka.acapi.check;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.actionables.Actionable;

public class AbstractCheckTest {

	@Test
	public void testDifferentInstances() {
		ActionableCheck root = Checker.whenActionable();
		ActionableCheck always = root.always();
		boolean result = always.not().always().check(mock(Actionable.class));
		assertThat(result).isFalse();
		assertThat(root.check(null)).isFalse();
	}
	
	@Test
	public void testChainedCalls() {
		StringCheck check = Checker.whenString().always().always().always().always();
		assertThat(check.check("")).isTrue();
		assertThat(check.testResults.size()).isEqualTo(4);
		
	}
	
}
