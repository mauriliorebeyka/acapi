package com.rebeyka.acapi.actionables;

import java.util.function.BiFunction;

import com.rebeyka.acapi.entities.Attribute;

//TODO this can probably be changed to something that takes only one attribute and a function that calculates on top of it
public class ChangeAttributeActionable<T extends Comparable<? super T>, U extends Comparable<? super U>> extends Actionable {

	private String sourceAttributeName;
	
	private String targetAttributeName;
	
	private BiFunction<Attribute<T>, Attribute<U>, U> newValue;
	
	public ChangeAttributeActionable(String actionableId, String sourceAttributeName, String targetAttributeName, BiFunction<Attribute<T>, Attribute<U>, U> newValue) {
		super(actionableId);
		this.sourceAttributeName = sourceAttributeName;
		this.targetAttributeName = targetAttributeName;
		this.newValue = newValue;
	}

	@Override
	public void execute() {
		Attribute<T> sourceAttribute = (Attribute<T>) getParent().getOrigin().getAttribute(sourceAttributeName);
		Attribute<U> targetAttribute = (Attribute<U>) getParent().getOrigin().getAttribute(targetAttributeName);
		targetAttribute.setValue(newValue.apply(sourceAttribute, targetAttribute));
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMessage() {
		return "Changing attribute %s on %s to %s".formatted(targetAttributeName,getParent().getOrigin(),getParent().getOrigin().getAttribute(targetAttributeName).getValue());
	}

	
}
