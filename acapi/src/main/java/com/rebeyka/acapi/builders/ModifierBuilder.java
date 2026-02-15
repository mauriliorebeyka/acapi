package com.rebeyka.acapi.builders;

import com.rebeyka.acapi.check.Checkable;
import com.rebeyka.acapi.check.Checker;
import com.rebeyka.acapi.entities.Playable;

public class ModifierBuilder {

	private Playable origin;

	private String attributeName;
	
	private boolean updateMin;

	private boolean updateMax;

	private boolean updateValue;

	private Checkable<Playable> condition;
	
	public ModifierBuilder() {
		this.condition = Checker.whenPlayable().always();
	}
	
	public ModifierBuilder withOrigin(Playable origin) {
		this.origin = origin;
		return this;
	}
	
	public ModifierBuilder withAttributeName(String attributeName) {
		this.attributeName = attributeName;
		return this;
	}
	
	public ModifierBuilder withUpdateMin(boolean updateMin) {
		this.updateMin = updateMin;
		return this;
	}
	
	public ModifierBuilder withUpdateMax(boolean updateMax) {
		this.updateMax = updateMax;
		return this;
	}
	
	public ModifierBuilder withUpdateValue(boolean updateValue) {
		this.updateValue = updateValue;
		return this;
	}
	
	public ModifierBuilder withCondition(Checkable<Playable> condition) {
		this.condition = condition;
		return this;
	}

	public Playable getOrigin() {
		return origin;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public boolean isUpdateMin() {
		return updateMin;
	}

	public boolean isUpdateMax() {
		return updateMax;
	}

	public boolean isUpdateValue() {
		return updateValue;
	}

	public Checkable<Playable> getCondition() {
		return condition;
	}
}
