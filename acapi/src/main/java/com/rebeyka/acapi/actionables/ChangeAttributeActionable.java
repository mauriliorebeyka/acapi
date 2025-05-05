package com.rebeyka.acapi.actionables;

import java.util.function.Function;

import com.rebeyka.acapi.entities.Attribute;

public class ChangeAttributeActionable<T extends Comparable<? super T>> extends Actionable {

	private Attribute<T> attribute;
	
	private Function<T, T> function;
	
	private T originalValue;
	
	public ChangeAttributeActionable(String actionableId, Attribute<T> attribute, Function<T, T> function) {
		super(actionableId);
		this.attribute = attribute;
		this.function = function;
	}

	@Override
	public void execute() {
		originalValue = attribute.getValue();
		attribute.setValue(function.apply(attribute.getValue()));
	}

	@Override
	public void rollback() {
		attribute.setValue(originalValue);
	}

	@Override
	public String getMessage() {
		return "Changing attribute %s on %s to %s".formatted(attribute.getName(),getParent().getOrigin(),attribute.getValue());
	}

	
}
