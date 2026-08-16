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
		PlayableCheck checker = Checker.whenPlayable();
		assertThat(checker.testResults).size().isEqualTo(0);
		checker = checker.not().isExactly(null);
		assertThat(checker.testResults).size().isEqualTo(1);
		assertThat(checker.attribute("title").testResults).size().isEqualTo(1);
		assertThat(checker.attribute("title").asString().testResults).size().isEqualTo(1);
		checker = checker.attribute("title").asString().isEqualsTo("A TITLE");
		assertThat(checker.testResults).size().isEqualTo(2);
		checker = checker.not().attribute("title").asString().isEqualsTo("TITLE");
		assertThat(checker.testResults).size().isEqualTo(3);
		checker = checker.isActivePlayer();
		assertThat(checker.testResults).size().isEqualTo(4);
		
	}
	
}
