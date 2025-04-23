package com.rebeyka.acapi.actionables.check;

import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Game;

public class TimelineCheck<BASE, T, ROOT extends AbstractCheck<BASE, T>> extends ValueCheck<BASE, T, Integer, ROOT> {

	private int times;

	private String bound;

	private Predicate<Integer> timesPredicate;

	private String timesPredicateDescription;

	protected TimelineCheck(ROOT root, Function<T, Game> gameAcessor, String testedField) {
		super(root, null, testedField);
		this.subFunction = f -> gameAcessor.apply(f).countActionables(getSearchedActionableId(f), bound);
		times = 1;
		timesPredicate = i -> i == times;
		timesPredicateDescription = "happened %s times since %s";
	}

	public TimelineCheck<BASE, T, ROOT> atLeast(int number) {
		times = number;
		timesPredicate = i -> i >= number;
		timesPredicateDescription = "happened at least %s times since %s";
		return this;
	}

	public ROOT since(String bound) {
		this.bound = bound;
		addValueTest(timesPredicateDescription.formatted(times, bound.equals("") ? "start" : bound), timesPredicate);
		return myself();
	}

	private String getSearchedActionableId(Object value) {
		if (testedField.equals("this actionable") && value instanceof Actionable actionable) {
			return actionable.getActionableId();
		} else {
			return testedField;
		}
	}

	public ROOT sinceStart() {
		return since("");
	}

}
