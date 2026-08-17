package com.rebeyka.acapi.check;

import java.util.function.Function;

public abstract class ValueCheck<BASE, ROOT extends Checkable<BASE>, T>
		extends AbstractCheck<ROOT, BASE, T> {

	protected Function<T, ?> valueAcessor;

	public ValueCheck(Checkable<BASE> root, Function<BASE, T> function) {
		super(root, function);
	}

	public BacktrackingIntegerCheck<BASE, ROOT> asInt() {
		return new BacktrackingIntegerCheck<>(this, p -> (int) valueAcessor.apply(function.apply(p)));
	}

	public BacktrackingStringCheck<BASE, ROOT> asString() {
		return new BacktrackingStringCheck<BASE, ROOT>(this, p -> (String) valueAcessor.apply(function.apply(p)));
	}
}
