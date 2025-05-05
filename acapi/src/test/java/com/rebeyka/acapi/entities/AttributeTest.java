package com.rebeyka.acapi.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AttributeTest {

	@Test
	public void testInitialization() {
		Attribute<Integer> attribute = new Attribute<>("attribute",Types.integer());
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
		Attribute<String> attribute = new Attribute<>("",Types.string());
		attribute.setValue("initial");
		attribute.setValue("changed");
		assertThat(attribute.getInitialValue()).isEqualTo("initial");
		assertThat(attribute.getValue()).isEqualTo("changed");
		assertThat(attribute.get()).isEqualTo("changed");
	}
	
	@Test
	public void testMaxValueBound() {
		Attribute<Integer> attribute = new Attribute<>("",Types.integer());
		attribute.setMaxValue(5);
		attribute.setValue(10);
		assertThat(attribute.getValue()).isEqualTo(5);
		attribute.setValue(2);
		assertThat(attribute.getValue()).isEqualTo(2);
		assertThat(attribute.get()).isEqualTo("2");
	}
	
	@Test
	public void testMinValueBound() {
		Attribute<Integer> attribute = new Attribute<>("",Types.integer());
		attribute.setMinValue(0);
		attribute.setValue(-2);
		assertThat(attribute.getValue()).isEqualTo(0);
		attribute.setValue(2);
		assertThat(attribute.getValue()).isEqualTo(2);
	}
	
	@Test
	public void testComparison() {
		Attribute<Integer> attribute = new Attribute<>("", Types.integer());
		attribute.setValue(5);
		Attribute<Integer> other = new Attribute<>("", 2, Types.integer());
		assertThat(attribute).isGreaterThan(other);
	}
}
