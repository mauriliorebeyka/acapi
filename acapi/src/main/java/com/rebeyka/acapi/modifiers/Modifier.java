package com.rebeyka.acapi.modifiers;

import java.util.function.Function;

import com.rebeyka.acapi.builders.ModifierBuilder;
import com.rebeyka.acapi.check.Checkable;
import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;

public class Modifier<T extends Comparable<? super T>> {

	private Playable origin;

	private String attributeName;

	private boolean updateMin;

	private boolean updateMax;

	private boolean updateValue;

	private Checkable<Playable> condition;

	private Function<T, T> function;

	public Modifier(ModifierBuilder builder, Function<T, T> function) {
		this.origin = builder.getOrigin();
		this.attributeName = builder.getAttributeName();
		this.updateMin = builder.isUpdateMin();
		this.updateMax = builder.isUpdateMax();
		this.updateValue = builder.isUpdateValue();
		this.condition = builder.getCondition();
		this.function = function;
	}

	public Playable getOrigin() {
		return origin;
	}

	public boolean isUpdateMin() {
		return updateMin;
	}

	public void setUpdateMin(boolean updateMin) {
		this.updateMin = updateMin;
	}

	public boolean isUpdateMax() {
		return updateMax;
	}

	public void setUpdateMax(boolean updateMax) {
		this.updateMax = updateMax;
	}

	public boolean isUpdateValue() {
		return updateValue;
	}

	public void setUpdateValue(boolean updateValue) {
		this.updateValue = updateValue;
	}

	public boolean valid(Playable playable, String attributeName) {
		return condition.check(playable) && attributeName.equals(this.attributeName)
				&& origin.getRawAttribute(attributeName).getType().equals(playable.getRawAttribute(attributeName).getType());
	}

	public Attribute<T> apply(Attribute<T> value) {
		Attribute<T> updatedAttribute = new Attribute<T>(value.getName(), value.getType());
		updatedAttribute.setMaxValue(isUpdateMax() ? function.apply(value.getMaxValue()) : value.getMaxValue());
		updatedAttribute.setMinValue(isUpdateMin() ? function.apply(value.getMinValue()) : value.getMinValue());
		updatedAttribute.setValue(isUpdateValue() ? function.apply(value.getValue()) : value.getValue());
		return updatedAttribute;
	}

}
