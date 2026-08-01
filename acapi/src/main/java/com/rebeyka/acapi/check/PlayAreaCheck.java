package com.rebeyka.acapi.check;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.PlayArea;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.view.VisibilityType;

public class PlayAreaCheck<BASE, ROOT extends AbstractCheck<?,?,BASE,?>, T extends PlayArea<Collection<?>, ?>>
		extends AbstractCheck<PlayAreaCheck<BASE, ROOT, T>, ROOT, BASE, T> {

	protected PlayAreaCheck(List<TestResult<BASE>> testResults, Function<BASE, T> function,
			Function<BASE, Game> gameAcessor) {
		super(testResults, function, gameAcessor);
	}

	@Override
	protected PlayAreaCheck<BASE, ROOT, T> self() {
		return new PlayAreaCheck<BASE, ROOT, T>(testResults, function, gameAcessor);
	}

	public ROOT empty() {
		return addTest(p -> p.getAll().isEmpty(), "", "is empty");
	}

	public IntegerCheck<BASE, ROOT> size() {
		return new IntegerCheck<>(root, p -> function.apply(p).getAll().size(), gameAcessor);
	}

	public ROOT constains(String id) {
		return addTest(p -> p.getAllPlayables().map(Playable::getId).anyMatch(v -> v.equals(id)), id, "contains");
	}
	
	public ROOT visibility(VisibilityType visibility) {
		return addTest(p -> p.getVisibilityType().equals(visibility), "visibility", "equals");
	}
	
	//TODO Needs method to check for occurrence group by a specific attribute.
	
	public PlayableCheck<BASE,ROOT> playable(String id) {
		return new PlayableCheck<BASE,ROOT>(testResults, p -> function.apply(p).get(id));
	}
}
