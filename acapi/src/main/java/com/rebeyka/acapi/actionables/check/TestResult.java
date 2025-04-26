package com.rebeyka.acapi.actionables.check;

import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;

public class TestResult<BASE> {

	private Predicate<BASE> predicate;

	private Function<BASE, ?> function;

	private String testedField;

	private String description;

	public TestResult(Predicate<BASE> predicate, Function<BASE, ?> function, String testedField, 
			String description) {
		this.predicate = predicate;
		this.function = function;
		this.testedField = testedField;
		this.description = description;
	}

	public boolean test(BASE testedValue) {
		return predicate.test(testedValue);
	}

	public String getMessage(BASE testedValue) {
		return "Checking that '%s' %s - currently '%s' (%s)".formatted(testedField,
				description, function.apply(testedValue), test(testedValue));
	}
}
