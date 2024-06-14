package com.rebeyka.acapi.random;

public class DieFace<T> {

	private T value;

	private double weight;

	private int intValue;

	public DieFace(T value, double weight, int intValue) {
		this.value = value;
		this.weight = weight;
		this.intValue = intValue;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

}
