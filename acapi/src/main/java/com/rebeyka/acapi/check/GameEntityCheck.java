package com.rebeyka.acapi.check;

import java.util.List;
import java.util.function.Function;

import com.rebeyka.acapi.entities.GameEntity;

public abstract class GameEntityCheck<BASE, ROOT extends AbstractCheck<?,BASE,?>, T extends GameEntity>
		extends AbstractCheck<ROOT, BASE, T> {
	
	protected GameEntityCheck(AbstractCheck<?,BASE,?> root, Function<BASE, T> function) {
		super(root, function);
	}
	
	public BacktrackingGameCheck<BASE,ROOT> game() {
		return new BacktrackingGameCheck<BASE,ROOT>(this, g -> function.apply(g).getGame());
	}
	
	public BacktrackingTimelineCheck<BASE,ROOT> happened() {
		return happened("");
	}
	
	public BacktrackingTimelineCheck<BASE, ROOT> happened(String actionableId) {
		return new BacktrackingTimelineCheck<>(this, g -> function.apply(g).getGame(), actionableId);
	}
	
	public BacktrackingStringCheck<BASE,ROOT> id() {
		return new BacktrackingStringCheck<BASE,ROOT>(this, e -> function.apply(e).getId());
	}
}
