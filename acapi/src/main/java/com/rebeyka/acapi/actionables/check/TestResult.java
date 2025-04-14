package com.rebeyka.acapi.actionables.check;

import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;

public class TestResult<T> {

	private Predicate<Actionable> predicate;
	
	private Function<T, Actionable> function;
	
	private String testedField;
	
	private T testedValue;
	
	private String description;
	
	public boolean test() {
		return predicate.test(function.apply(testedValue));
	}
	
	public String getMessage() {
		return "Expecting %s to be %s %s".formatted(testedField,description,testedValue);
	}
}
