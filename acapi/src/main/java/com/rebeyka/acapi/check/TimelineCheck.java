package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.gameflow.EndRoundActionable;
import com.rebeyka.acapi.actionables.gameflow.EndTurnActionable;
import com.rebeyka.acapi.entities.Game;

public class TimelineCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>>
		extends AbstractCheck<TimelineCheck<BASE, ROOT>, ROOT, BASE, Integer> {

	private int times;

	private String bound;

	private Predicate<Integer> timesPredicate;

	private String predicateDescription;

	private String searchedActionable;
	
	private Function<BASE, Game> gameAcessor;

	protected TimelineCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, Game> gameAcessor, String searchedActionable) {
		super(root, testResults, null, gameAcessor);
		this.function = f -> gameAcessor.apply(f).countActionables(getSearchedActionableId(f), bound);
		this.gameAcessor = gameAcessor;
		this.searchedActionable = searchedActionable;
		atLeast(1);
	}

	@Override
	protected TimelineCheck<BASE, ROOT> self(List<TestResult<BASE>> testResults) {
		return new TimelineCheck<>(root, testResults, gameAcessor, searchedActionable);
	}

	public TimelineCheck<BASE, ROOT> atLeast(int number) {
		times = number;
		timesPredicate = i -> i >= number;
		predicateDescription = "happened at least %s times since %s";
		return this;
	}

	public TimelineCheck<BASE, ROOT> atLeastOnce() {
		return atLeast(1);
	}

	public TimelineCheck<BASE, ROOT> atMost(int number) {
		times = number;
		timesPredicate = i -> i <= number;
		predicateDescription = "happened at most %s times since %s";
		return this;
	}

	public TimelineCheck<BASE, ROOT> atMostOnce() {
		return atMost(1);
	}

	public TimelineCheck<BASE, ROOT> exactly(int number) {
		times = number;
		timesPredicate = i -> i == number;
		predicateDescription = "happened exactly %s times since %s";
		return this;
	}

	public TimelineCheck<BASE, ROOT> once() {
		return exactly(1);
	}

	public ROOT since(String bound) {
		this.bound = bound;
		return addTest(timesPredicate, predicateDescription.formatted(times, bound.equals("") ? "start" : bound), "happened since");
	}

	public ROOT sinceStart() {
		return since("");
	}

	public ROOT thisTurn() {
		return since(EndTurnActionable.ID);
	}

	public ROOT thisRound() {
		return since(EndRoundActionable.ID);
	}

	public ROOT last(int x) {
		this.function = f -> gameAcessor.apply(f).countActionables(getSearchedActionableId(f), x);
		return addTest(timesPredicate, predicateDescription.formatted(times, x), "happened in the last");
	}

	public ROOT last() {
		return last(1);
	}

	private String getSearchedActionableId(Object value) {
		if (searchedActionable.isBlank() && value instanceof Actionable actionable) {
			return actionable.getActionableId();
		} else {
			return searchedActionable;
		}
	}

}
