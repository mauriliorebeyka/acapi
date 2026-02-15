package com.rebeyka.acapi.actionables;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;

public class ChangeAttributeActionable<T extends Comparable<? super T>> extends Actionable {

	private Playable origin;
	
	private Attribute<T> attribute;

	private Function<T, T> function;

	private T originalValue;
	
	public ChangeAttributeActionable(String actionableId, Playable origin, Attribute<T> attribute, Function<T, T> function) {
		super(actionableId);
		this.origin = origin;
		this.attribute = attribute;
		this.function = function;
	}

	public ChangeAttributeActionable(String actionableId, Playable origin, Attribute<T> attribute, T value) {
		this(actionableId, origin, attribute, _ -> value);
	}

	public ChangeAttributeActionable(String actionableId, Playable origin, Attribute<T> attribute, T value,
			BiFunction<T, T, T> function) {
		this(actionableId, origin, attribute, _ -> function.apply(attribute.getValue(), value));
	}

	@Override
	public void execute() {
		originalValue = attribute.getValue();
		attribute.setValue(function.apply(origin.getGame().getModifiedAttribute(origin, attribute).getValue()));
	}

	@Override
	public void rollback() {
		attribute.setValue(originalValue);
	}

	@Override
	public String getMessage() {
		return "Changing attribute %s on %s to %s".formatted(attribute.getName(), getParent().getOrigin(),
				attribute.getValue());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Supplier<Actionable> supply() {
		return () -> new ChangeAttributeActionable(getActionableId(), origin, attribute, function);
	}
}
