package com.rebeyka.acapi.entities;

public class SimpleIntegerAttribute extends Attribute<Integer> {

	private int maxValue;

	public SimpleIntegerAttribute(String name, int value, int maxValue) {
		super(name, value);
		this.maxValue = maxValue;
	}

	public SimpleIntegerAttribute(String name, int value) {
		this(name, value, value);
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

}
