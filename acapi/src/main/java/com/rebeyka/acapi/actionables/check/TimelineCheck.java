package com.rebeyka.acapi.actionables.check;

import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Game;

//TODO This can probably be changed to extend ValueCheck<BASE, T, Integer, ROOT> - keeping in line with the other subclasses of ValueCheck
public class TimelineCheck<BASE, T, S, ROOT extends AbstractCheck<BASE, T>> extends ValueCheck<BASE, T, S, ROOT> {

	private int times;

	private Function<T, Game> gameAcessor;

	protected TimelineCheck(ROOT root, Function<T, S> subFunction, String testedField, Function<T, Game> gameAcessor) {
		super(root, subFunction, testedField);
		times = 1;
		this.gameAcessor = gameAcessor;
	}

	public ROOT since(String bound) {
		Predicate<Integer> intPred = i -> i == times;
		Predicate<T> pred = f -> intPred.test(gameAcessor.apply(f).countActionables(getSearchedActionableId(f), bound));
		addTest(pred, f -> gameAcessor.apply(f).countActionables(getSearchedActionableId(f), ""),
				testedField == "" ? "this actionable" : testedField,
				"happened %s times since %s".formatted(times, bound));
		return myself();
	}

	private String getSearchedActionableId(Object value) {
		return testedField == "" ? value instanceof Actionable actionable ? actionable.getActionableId() : ""
				: testedField;
	}

	public ROOT sinceStart() {
		return since("");
	}

}
