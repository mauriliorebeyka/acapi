package com.rebeyka.acapi.check;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.Game;
import com.rebeyka.acapi.entities.PlayArea;
import com.rebeyka.acapi.entities.Playable;
import com.rebeyka.acapi.view.VisibilityType;

public class PlayAreaCheck<BASE, T extends PlayArea<Collection<?>, ?>>
		extends AbstractCheck<PlayAreaCheck<BASE, T>, BASE, T> {

	protected PlayAreaCheck(List<TestResult<BASE>> testResults, Function<BASE, T> function,
			Function<BASE, Game> gameAcessor) {
		super(testResults, function, gameAcessor);
	}

	@Override
	protected PlayAreaCheck<BASE, T> self() {
		return new PlayAreaCheck<BASE, T>(testResults, function, gameAcessor);
	}

	public PlayAreaCheck<BASE, T> empty() {
		return addTest(p -> p.getAll().isEmpty(), "", "is empty");
	}

	public IntegerCheck<BASE, PlayAreaCheck<BASE, T>> size() {
		return new IntegerCheck<>(this, p -> function.apply(p).getAll().size(), "size", gameAcessor);
	}

	public PlayAreaCheck<BASE, T> constains(String id) {
		return addTest(p -> p.getAllPlayables().map(Playable::getId).anyMatch(v -> v.equals(id)), id, "contains");
	}
	
	public PlayAreaCheck<BASE, T> visibility(VisibilityType visibility) {
		return addTest(p -> p.getVisibilityType().equals(visibility), "visibility", "equals");
	}
	
	//TODO Needs method to check for occurrence group by a specific attribute.
	
	public PlayableCheck<BASE> playable(String id) {
		return new PlayableCheck<BASE>(testResults, p -> function.apply(p).get(id));
	}
}
