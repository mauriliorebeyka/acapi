package com.rebeyka.acapi.check;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;

@SuppressWarnings("rawtypes")
public class AttributeCheckTest {

	@Mock
	private Playable playable;
	
	@Mock
	private Attribute attribute;
	
	@Mock
	private Attribute rawAttribute;
	
	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		MockitoAnnotations.openMocks(this);
		when(playable.getAttribute("attribute")).thenReturn(attribute);
		when(playable.getRawAttribute("attribute")).thenReturn(rawAttribute);
		when(attribute.getMinValue()).thenReturn(0);
		when(attribute.getInitialValue()).thenReturn(1);
		when(attribute.getValue()).thenReturn(2);
		when(attribute.getMaxValue()).thenReturn(3);
		when(rawAttribute.getMinValue()).thenReturn("0");
		when(rawAttribute.getInitialValue()).thenReturn("1");
		when(rawAttribute.getValue()).thenReturn("2");
		when(rawAttribute.getMaxValue()).thenReturn("3");
		
		assertThat(Checker.whenPlayable().attribute("attribute").asInt().isEqualsTo(1).check(playable)).isFalse();
		assertThat(Checker.whenPlayable().attribute("attribute").min().asInt().isEqualsTo(0).check(playable)).isTrue();
		assertThat(Checker.whenPlayable().attribute("attribute").initial().asInt().isEqualsTo(1).check(playable)).isTrue();
		assertThat(Checker.whenPlayable().attribute("attribute").asInt().isEqualsTo(2).check(playable)).isTrue();
		assertThat(Checker.whenPlayable().attribute("attribute").max().asInt().isEqualsTo(3).check(playable)).isTrue();
		assertThat(Checker.whenPlayable().attribute("attribute").raw().min().asString().isEqualsTo("0").check(playable)).isTrue();
		assertThat(Checker.whenPlayable().attribute("attribute").raw().initial().asString().isEqualsTo("1").check(playable)).isTrue();
		assertThat(Checker.whenPlayable().attribute("attribute").raw().asString().isEqualsTo("2").check(playable)).isTrue();
		assertThat(Checker.whenPlayable().attribute("attribute").raw().max().asString().isEqualsTo("3").check(playable)).isTrue();
	}
}
