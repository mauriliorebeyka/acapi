package com.rebeyka.acapi.view;

public class AttributeView<T> {

	private String attributeName;

	private T attributeValue;

	public AttributeView(String attributeName, T attributeValue) {
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

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof AttributeView<?> attribute) {
			return this.getAttributeName().equals(attribute.getAttributeName())
					&& this.getAttributeValue().equals(attribute.getAttributeValue());
		}
		return false;
	}

	@Override
	public String toString() {
		if (attributeValue == null) {
			return "Name: %s / no type".formatted(attributeName);
		} else {
			return "Name: %s / Type: %s / Value: %s".formatted(attributeName, attributeValue.getClass(),
					attributeValue);
		}
	}
}
