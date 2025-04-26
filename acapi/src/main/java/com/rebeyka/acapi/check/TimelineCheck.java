package com.rebeyka.acapi.check;

import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.entities.Game;

public class TimelineCheck<BASE, T, ROOT extends AbstractCheck<?,BASE,T>>
extends ValueCheck<TimelineCheck<BASE,T,ROOT>, BASE, Integer, ROOT> {

	private int times;

	private String bound;

	private Predicate<Integer> timesPredicate;

	private String predicateDescription;

	protected TimelineCheck(ROOT root, Function<BASE, Game> gameAcessor, String testedField) {
		super(root, null, testedField);
		this.function = f -> gameAcessor.apply(f).countActionables(getSearchedActionableId(f), bound);
		times = 1;
		timesPredicate = i -> i == times;
		predicateDescription = "happened %s times since %s";
	}

	public TimelineCheck<BASE, T, ROOT> atLeast(int number) {
		times = number;
		timesPredicate = i -> i >= number;
		predicateDescription = "happened at least %s times since %s";
		return this;
	}

	public ROOT since(String bound) {
		this.bound = bound;
		addValueTest(predicateDescription.formatted(times, bound.equals("") ? "start" : bound), timesPredicate);
		return root();
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
