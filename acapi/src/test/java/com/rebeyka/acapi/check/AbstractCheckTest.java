package com.rebeyka.acapi.check;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Playable;

public class AbstractCheckTest {

	@Test
	public void testDifferentInstances() {
		ActionableCheck root = Checker.whenActionable();
		ActionableCheck always = root.always();
		boolean result = always.not().always().check(mock(Actionable.class));
		assertThat(result).isFalse();
		assertThat(root.check(mock(Actionable.class))).isFalse();
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

	@Test
	public void testAnyOf() {
		assertThat(Checker.whenActionable().anyOf().check(mock(Actionable.class))).isFalse();
		assertThat(
				Checker.whenActionable().anyOf(Checker.whenActionable().not().always()).check(mock(Actionable.class)))
				.isFalse();
		assertThat(Checker.whenActionable()
				.anyOf(Checker.whenActionable().not().always(), Checker.whenActionable().always())
				.check(mock(Actionable.class))).isTrue();
	}

	@Test
	public void testAllOf() {
		PlayableCheck check = Checker.whenPlayable();
		assertThat(check.allOf().check(mock(Playable.class))).isFalse();
		assertThat(check.allOf(check.always()).check(mock(Playable.class))).isTrue();
		assertThat(check.allOf(check.always(), check.not().always()).check(mock(Playable.class))).isFalse();
	}

	@Test
	public void testIsExactly() {
		PlayableCheck check = Checker.whenPlayable();
		Playable playable = mock(Playable.class);
		assertThat(check.isEqualsTo(null).check(playable)).isFalse();
		assertThat(check.isEqualsTo(mock(Playable.class)).check(playable)).isFalse();
		assertThat(check.isEqualsTo(playable).check(playable)).isTrue();
	}
	
	@Test
	public void testNullCheck() {
		assertThatThrownBy(() -> Checker.whenActionable().check(null)).isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void testNot() {
		PlayableCheck check = Checker.whenPlayable();
		Playable playable = mock(Playable.class);
		assertThat(check.always().check(playable)).isTrue();
		assertThat(check.not().always().check(playable)).isFalse();
		assertThat(check.not().not().always().check(playable)).isTrue();
		//Not is after the last check, so shouldn't be considered
		assertThat(check.always().not().check(playable)).isTrue();
	}
}
