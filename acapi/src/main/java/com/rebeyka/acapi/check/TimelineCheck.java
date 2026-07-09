package com.rebeyka.acapi.check;

import java.util.function.Function;
import java.util.function.Predicate;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.actionables.gameflow.EndRoundActionable;
import com.rebeyka.acapi.actionables.gameflow.EndTurnActionable;
import com.rebeyka.acapi.entities.Game;

public class TimelineCheck<BASE, T, ROOT extends AbstractCheck<?,BASE,T>>
extends ValueCheck<TimelineCheck<BASE,T,ROOT>, BASE, Integer, ROOT> {

	private int times;

	private String bound;

	private Predicate<Integer> timesPredicate;

	private String predicateDescription;

	private Function<BASE, Game> gameAcessor;
	
	protected TimelineCheck(ROOT root, Function<BASE, Game> gameAcessor, String actionableId) {
		super(root, null, actionableId, gameAcessor);
		this.function = f -> gameAcessor.apply(f).countActionables(getSearchedActionableId(f), bound);
		this.gameAcessor = gameAcessor;
		atLeast(1);
	}

	public TimelineCheck<BASE, T, ROOT> atLeast(int number) {
		times = number;
		timesPredicate = i -> i >= number;
		predicateDescription = "happened at least %s times since %s";
		return this;
	}
	
	public TimelineCheck<BASE, T, ROOT> atLeastOnce() {
		return atLeast(1);
	}
	
	public TimelineCheck<BASE, T, ROOT> atMost(int number) {
		times = number;
		timesPredicate = i -> i <= number;
		predicateDescription = "happened at most %s times since %s";
		return this;
	}
	
	public TimelineCheck<BASE, T, ROOT> atMostOnce() {
		return atMost(1);
	}
	
	public TimelineCheck<BASE, T, ROOT> exactly(int number) {
		times = number;
		timesPredicate = i -> i == number;
		predicateDescription = "happened exactly %s times since %s";
		return this;
	}
	
	public TimelineCheck<BASE, T, ROOT> once() {
		return exactly(1);
	}
	
	public ROOT since(String bound) {
		this.bound = bound;
		addValueTest(predicateDescription.formatted(times, bound.equals("") ? "start" : bound), timesPredicate);
		return root();
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
		addValueTest(predicateDescription.formatted(times, x), timesPredicate);
		return root();
	}
	
	public ROOT last() {
		return last(1);
	}
	
	private String getSearchedActionableId(Object value) {
		if (testedField.isBlank() && value instanceof Actionable actionable) {
			return actionable.getActionableId();
		} else {
			return testedField;
		}
	}
	
}
