package com.rebeyka.acapi.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AttributeTest {

	@Mock
	private Playable mockPlayable;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testInitialization() {
		Attribute<Integer> attribute = new Attribute<>("attribute", Types.integer(), mockPlayable);
		when(mockPlayable.getRawAttribute("", Types.integer())).thenReturn(attribute);
		assertThat(attribute.getValue()).isNull();
		assertThat(attribute.getInitialValue()).isNull();
		assertThat(attribute.getMinValue()).isNull();
		assertThat(attribute.getMaxValue()).isNull();
		assertThat(attribute.getName()).isEqualTo("attribute");
		assertThat(attribute.get()).isEqualTo("null");
		assertThat(attribute.getType()).isEqualTo(Types.integer());
	}

	@Test
	public void testInitialValueIsConstant() {
		Attribute<String> attribute = new Attribute<>("", Types.string(), mockPlayable);
		when(mockPlayable.getRawAttribute("", Types.string())).thenReturn(attribute);
		attribute.setValue("initial");
		attribute.setValue("changed");
		assertThat(attribute.getInitialValue()).isEqualTo("initial");
		assertThat(attribute.getValue()).isEqualTo("changed");
		assertThat(attribute.get()).isEqualTo("changed");
	}

	@Test
	public void testMaxValueBound() {
		Attribute<Integer> attribute = new Attribute<>("", Types.integer(), mockPlayable);
		when(mockPlayable.getRawAttribute("", Types.integer())).thenReturn(attribute);
		attribute.setMaxValue(5);
		attribute.setValue(10);
		assertThat(attribute.getValue()).isEqualTo(5);
		attribute.setValue(2);
		assertThat(attribute.getValue()).isEqualTo(2);
		assertThat(attribute.get()).isEqualTo("2");
	}

	@Test
	public void testMinValueBound() {
		Attribute<Integer> attribute = new Attribute<>("", Types.integer(), mockPlayable);
		when(mockPlayable.getRawAttribute("", Types.integer())).thenReturn(attribute);
		attribute.setMinValue(0);
		attribute.setValue(-2);
		assertThat(attribute.getValue()).isEqualTo(0);
		attribute.setValue(2);
		assertThat(attribute.getValue()).isEqualTo(2);
	}

	@Test
	public void testComparison() {
		Attribute<Integer> attribute = new Attribute<>("", Types.integer(), mockPlayable);
		when(mockPlayable.getRawAttribute("", Types.integer())).thenReturn(attribute);
		attribute.setValue(5);
		Attribute<Integer> other = new Attribute<>("", 2, Types.integer(), null);
		assertThat(attribute).isGreaterThan(other);
	}
}
