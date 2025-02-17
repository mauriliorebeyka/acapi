package com.rebeyka.acapi.actionables;

import java.util.function.BiFunction;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;

public class ChangeAttributeActionable<T extends Comparable<? super T>, U extends Comparable<? super U>> extends Actionable {

	private String sourceAttributeName;
	
	private String targetAttributeName;
	
	private BiFunction<Attribute<T>, Attribute<U>, U> newValue;
	
	public ChangeAttributeActionable(String actionableId, Playable playable, String sourceAttributeName, String targetAttributeName, BiFunction<Attribute<T>, Attribute<U>, U> newValue) {
		super(actionableId, playable);
		this.sourceAttributeName = sourceAttributeName;
		this.targetAttributeName = targetAttributeName;
		this.newValue = newValue;
	}

	@Override
	public void execute() {
		Attribute<T> sourceAttribute = (Attribute<T>) getPlayable().getAttribute(sourceAttributeName);
		Attribute<U> targetAttribute = (Attribute<U>) getPlayable().getAttribute(targetAttributeName);
		targetAttribute.setValue(newValue.apply(sourceAttribute, targetAttribute));
	}

	@Override
	public void rollback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMessage() {
		return "Changing attribute %s on %s to %s".formatted(targetAttributeName,getPlayable(),getPlayable().getAttribute(targetAttributeName).getValue());
	}

	
}
