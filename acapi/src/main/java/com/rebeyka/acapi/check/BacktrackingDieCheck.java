package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.random.Die;

public class BacktrackingDieCheck<BASE, ROOT extends Checkable<BASE>>
		extends ValueCheck<BASE, ROOT, Die<?>> {

	protected BacktrackingDieCheck(Checkable<BASE> root, Function<BASE, Die<?>> function) {
		super(root, function);
		this.valueAcessor = d -> d.getValue();
	}


	public ROOT isRolled() {
		return addTest(p -> p.isRolled(), "is", "rolled");
	}

}
