package com.rebeyka.acapi.entities;

public class Attribute<T extends Comparable<? super T>> implements Comparable<Attribute<T>> {

	private T value;

	public Attribute(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public int compareTo(Attribute<T> o) {
		return this.getValue().compareTo(o.getValue());
	}
}
