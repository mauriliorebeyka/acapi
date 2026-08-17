package com.rebeyka.acapi.check;

import java.util.Collection;
import java.util.function.Function;

import com.rebeyka.acapi.entities.PlayArea;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.view.VisibilityType;

public class BacktrackingPlayAreaCheck<BASE, ROOT extends Checkable<BASE>, T extends PlayArea<Collection<?>, ?>>
		extends AbstractCheck<ROOT, BASE, T> {

	protected BacktrackingPlayAreaCheck(Checkable<BASE> root, Function<BASE, T> function) {
		super(root, function);
	}

	public ROOT empty() {
		return addTest(p -> p.getAll().isEmpty(), "", "is empty");
	}

	public BacktrackingIntegerCheck<BASE, ROOT> size() {
		return new BacktrackingIntegerCheck<>(this, p -> function.apply(p).getAll().size());
	}

	public ROOT constains(String id) {
		return addTest(p -> p.getAllPlayables().map(Playable::getId).anyMatch(v -> v.equals(id)), id, "contains");
	}
	
	public ROOT visibility(VisibilityType visibility) {
		return addTest(p -> p.getVisibilityType().equals(visibility), "visibility", "equals");
	}
	
	//TODO Needs method to check for occurrence group by a specific attribute.
	
	public BacktrackingPlayableCheck<BASE,ROOT> playable(String id) {
		return new BacktrackingPlayableCheck<BASE,ROOT>(this, p -> function.apply(p).get(id));
	}
}
