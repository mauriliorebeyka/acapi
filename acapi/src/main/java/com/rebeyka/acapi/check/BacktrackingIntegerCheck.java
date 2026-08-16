package com.rebeyka.acapi.check;

import java.util.function.Function;

public class BacktrackingIntegerCheck<BASE, ROOT extends AbstractCheck<?,BASE,?>>
		extends AbstractCheck<ROOT, BASE, Integer> {

	protected BacktrackingIntegerCheck(AbstractCheck<?,BASE,?> root, Function<BASE, Integer> function) {
		super(root, function);
	}

	public ROOT biggerThan(int value) {
		return addTest(i -> i > value, Integer.toString(value), "is bigger than");
	}

	public ROOT lessThan(int value) {
		return addTest(i -> i < value, Integer.toString(value), "is less than");
	}

	public ROOT between(int start, int end) {
		return addTest(i -> i >= start && i <= end, "%s and %s".formatted(start, end), "between");
	}
}
