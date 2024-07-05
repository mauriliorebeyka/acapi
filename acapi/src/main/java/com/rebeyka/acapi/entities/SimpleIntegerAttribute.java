package com.rebeyka.acapi.entities;

public class SimpleIntegerAttribute extends Attribute<Integer> {

	private int maxValue;

	public SimpleIntegerAttribute(int value, int maxValue) {
		super(value);
		this.maxValue = maxValue;
	}

	public SimpleIntegerAttribute(int value) {
		this(value, value);
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

}
