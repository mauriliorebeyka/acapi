package com.rebeyka.acapi.check;

import java.util.function.Function;

public class BacktrackingStringCheck<BASE, ROOT extends AbstractCheck<?,BASE,?>>
		extends AbstractCheck<ROOT, BASE, String> {

	protected BacktrackingStringCheck(AbstractCheck<?,BASE,?> root, Function<BASE, String> function) {
		super(root, function);
	}

	public ROOT contains(String value) {
		return addTest(s -> s.contains(value), value, "contains");
	}

}
