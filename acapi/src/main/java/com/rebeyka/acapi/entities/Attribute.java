package com.rebeyka.acapi.entities;

import com.google.common.reflect.TypeToken;

public class Attribute<T extends Comparable<? super T>> implements Comparable<Attribute<T>> {

	private String name;

	private Playable origin;

	private T value;

	private T initialValue;

	private T maxValue;

	private T minValue;

	private TypeToken<T> type;

	public Attribute(String name, T value, TypeToken<T> type, Playable origin) {
		this.name = name;
		this.value = value;
		this.type = type;
		this.origin = origin;
	}

	public Attribute(String name, TypeToken<T> type, Playable origin) {
		this(name, null, type, origin);
	}

	public String getName() {
		return name;
	}

	public Playable getOrigin() {
		return origin;
	}
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		T finalValue = value;
		if (getMinValue() != null && value.compareTo(getMinValue()) < 0) {
			finalValue = getMinValue();
		}
		if (getMaxValue() != null && value.compareTo(getMaxValue()) > 0) {
			finalValue = getMaxValue();
		}
		if (this.initialValue == null) {
			this.initialValue = finalValue;
		}
		this.value = finalValue;
	}

	public T getInitialValue() {
		return initialValue;
	}
	
	public T getMinValue() {
		return minValue;
	}

	public void setMinValue(T minValue) {
		this.minValue = minValue;
	}

	public T getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(T maxValue) {
		this.maxValue = maxValue;
	}

	public TypeToken<T> getType() {
		return type;
	}

	public String get() {
		return value == null ? "null" : value.toString();
	}

	@Override
	public int compareTo(Attribute<T> o) {
		if (o == null || o.getValue() == null) {
			return Integer.MIN_VALUE;
		}
		return getValue().compareTo(o.getValue());
	}
}
