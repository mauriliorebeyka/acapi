package com.rebeyka.acapi.check;

import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.gameflow.EndRoundActionable;
import com.rebeyka.acapi.actionables.gameflow.EndTurnActionable;
import com.rebeyka.acapi.entities.Game;

public class TimelineCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, T>, T>
		extends AbstractCheck<TimelineCheck<BASE, ROOT, T>, ROOT, BASE, Integer> {

	private int times;

	private String bound;

	private Predicate<Integer> timesPredicate;

	private String predicateDescription;

	private String searchedActionable;
	
	private Function<BASE, Game> gameAcessor;

	protected TimelineCheck(ROOT root, Function<BASE, Game> gameAcessor, String searchedActionable) {
		super(root, null, gameAcessor);
		this.function = f -> gameAcessor.apply(f).countActionables(getSearchedActionableId(f), bound);
		this.gameAcessor = gameAcessor;
		this.searchedActionable = searchedActionable;
		atLeast(1);
	}

	@Override
	protected TimelineCheck<BASE, ROOT, T> self() {
		return new TimelineCheck<>(root, gameAcessor, searchedActionable);
	}

	public TimelineCheck<BASE, ROOT, T> atLeast(int number) {
		times = number;
		timesPredicate = i -> i >= number;
		predicateDescription = "happened at least %s times since %s";
		return this;
	}

	public TimelineCheck<BASE, ROOT, T> atLeastOnce() {
		return atLeast(1);
	}

	public TimelineCheck<BASE, ROOT, T> atMost(int number) {
		times = number;
		timesPredicate = i -> i <= number;
		predicateDescription = "happened at most %s times since %s";
		return this;
	}

	public TimelineCheck<BASE, ROOT, T> atMostOnce() {
		return atMost(1);
	}

	public TimelineCheck<BASE, ROOT, T> exactly(int number) {
		times = number;
		timesPredicate = i -> i == number;
		predicateDescription = "happened exactly %s times since %s";
		return this;
	}

	public TimelineCheck<BASE, ROOT, T> once() {
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
