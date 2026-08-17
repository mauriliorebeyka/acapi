package com.rebeyka.acapi.check;

import java.util.function.Function;

public class BacktrackingStringCheck<BASE, ROOT extends Checkable<BASE> & RootChecker<BASE, ROOT>>
		extends AbstractCheck<ROOT, BASE, String> {

	protected BacktrackingStringCheck(Checkable<BASE> root, Function<BASE, String> function) {
		super(root, function);
	}

	public ROOT contains(String value) {
		return addTest(s -> s.contains(value), value, "contains");
	}

}
