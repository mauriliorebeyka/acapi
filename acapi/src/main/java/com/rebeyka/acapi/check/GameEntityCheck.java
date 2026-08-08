package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.GameEntity;

public abstract class GameEntityCheck<BASE, ROOT extends AbstractCheck<?, ?, BASE, ?>, T extends GameEntity>
		extends AbstractCheck<GameEntityCheck<BASE, ROOT, T>, ROOT, BASE, T> {
	
	protected GameEntityCheck(ROOT root, List<TestResult<BASE>> testResults, Function<BASE, T> function) {
		super(root, testResults, function);
	}

	protected GameEntityCheck(List<TestResult<BASE>> testResults, Function<BASE, T> function) {
		super(testResults, function);
	}
	
	public BacktrackingGameCheck<BASE,ROOT> game() {
		return new BacktrackingGameCheck<BASE,ROOT>(root, testResults, g -> function.apply(g).getGame());
	}
	
	public BacktrackingTimelineCheck<BASE,ROOT> happened() {
		return happened("");
	}
	
	public BacktrackingTimelineCheck<BASE, ROOT> happened(String actionableId) {
		return new BacktrackingTimelineCheck<>(root, testResults, g -> function.apply(g).getGame(), actionableId);
	}
	
	public BacktrackingStringCheck<BASE,ROOT> id() {
		return new BacktrackingStringCheck<BASE,ROOT>(root, testResults, e -> function.apply(e).getId());
	}
}
