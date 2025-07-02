package com.rebeyka.acapi.view;

public class ValueView<T> {

	private String attributeName;
	
	private T attributeValue;

	public ValueView(String attributeName, T attributeValue) {
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public T getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(T attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	
}
