package com.rebeyka.acapi.check;

import java.util.function.Function;

import com.rebeyka.acapi.actionables.Actionable;
public class BacktrackingActionableCheck<BASE,ROOT extends Checkable<BASE> & RootChecker<BASE, ROOT>> extends GameEntityCheck<BASE, ROOT, Actionable> {
	
	protected BacktrackingActionableCheck(Checkable<BASE> root, Function<BASE, Actionable> function) {
		super(root, function);
	}
	
	public BacktrackingPlayableCheck<BASE,ROOT> origin() {
		return new BacktrackingPlayableCheck<>(this, t -> function.apply(t).getParent().getOrigin());
	}
    
}
