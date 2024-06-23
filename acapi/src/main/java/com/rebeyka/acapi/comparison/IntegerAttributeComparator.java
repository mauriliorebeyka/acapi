package com.rebeyka.acapi.comparison;

import com.rebeyka.acapi.entities.Attribute;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.entities.SimpleIntegerAttribute;

public class IntegerAttributeComparator {

	public static boolean actualEquals(Playable playable, String attributeName, int value) {
		Attribute<?> compared = playable.getAttribute(attributeName);
		if (compared != null && compared instanceof SimpleIntegerAttribute intAttribute) {
			return intAttribute.getValue() == value;
		} else {
			return false;
		}
	}

}
