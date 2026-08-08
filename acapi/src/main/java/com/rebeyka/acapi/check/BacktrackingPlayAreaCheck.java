package com.rebeyka.acapi.check;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.PlayArea;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.view.VisibilityType;

public class BacktrackingPlayAreaCheck<BASE, ROOT extends AbstractCheck<?,?,BASE,?>, T extends PlayArea<Collection<?>, ?>>
		extends AbstractCheck<BacktrackingPlayAreaCheck<BASE, ROOT, T>, ROOT, BASE, T> {

	protected BacktrackingPlayAreaCheck(List<TestResult<BASE>> testResults, Function<BASE, T> function) {
		super(testResults, function);
	}

	@Override
	protected BacktrackingPlayAreaCheck<BASE, ROOT, T> self(List<TestResult<BASE>> testResults) {
		return new BacktrackingPlayAreaCheck<BASE, ROOT, T>(testResults, function);
	}

	public ROOT empty() {
		return addTest(p -> p.getAll().isEmpty(), "", "is empty");
	}

	public BacktrackingIntegerCheck<BASE, ROOT> size() {
		return new BacktrackingIntegerCheck<>(root, testResults, p -> function.apply(p).getAll().size());
	}

	public ROOT constains(String id) {
		return addTest(p -> p.getAllPlayables().map(Playable::getId).anyMatch(v -> v.equals(id)), id, "contains");
	}
	
	public ROOT visibility(VisibilityType visibility) {
		return addTest(p -> p.getVisibilityType().equals(visibility), "visibility", "equals");
	}
	
	//TODO Needs method to check for occurrence group by a specific attribute.
	
	public BacktrackingPlayableCheck<BASE,ROOT> playable(String id) {
		return new BacktrackingPlayableCheck<BASE,ROOT>(testResults, p -> function.apply(p).get(id));
	}
}
